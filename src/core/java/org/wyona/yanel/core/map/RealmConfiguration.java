/*
 * Copyright 2006 Wyona
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.wyona.org/licenses/APACHE-LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wyona.yanel.core.map;

import java.io.File;
import java.lang.ClassNotFoundException;
import java.lang.IllegalAccessException;
import java.lang.InstantiationException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Category;

import org.wyona.commons.io.FileUtil;

import org.wyona.yanel.core.Resource;
import org.wyona.yanel.core.ResourceTypeDefinition;

/**
 *
 */
public class RealmConfiguration {

    private static Category log = Category.getInstance(RealmConfiguration.class);

    public static final String DEFAULT_CONFIGURATION_FILE = "yanel.properties";
    public static String CONFIGURATION_FILE = DEFAULT_CONFIGURATION_FILE;

    private URL propertiesURL;

    public String proxyHostName;
    public String proxyPort;
    public String proxyPrefix;

    java.util.HashMap hm = new java.util.HashMap();

    /**
     *
     */
    public RealmConfiguration() {
        this(DEFAULT_CONFIGURATION_FILE);
    }

    /**
     *
     */
    public RealmConfiguration(String configurationFile) {
        CONFIGURATION_FILE = configurationFile;

        propertiesURL = RealmConfiguration.class.getClassLoader().getResource(CONFIGURATION_FILE);
        if (propertiesURL == null) {
            log.error("No such resource: " + CONFIGURATION_FILE);
            return;
        }

        Properties props = new Properties();
        try {
            props.load(propertiesURL.openStream());
            File propsFile = new File(propertiesURL.getFile());
            String separator = ",";
            String[] tokens = props.getProperty("resources").split(separator);
            for (int i = 0; i < tokens.length; i++) {
                File resConfigFile = new File(tokens[i]);
                if (!resConfigFile.isAbsolute()) {
                    resConfigFile = FileUtil.file(propsFile.getParentFile().getAbsolutePath(), tokens[i]);
                }

                if (resConfigFile.exists()) {
                    ResourceTypeDefinition rtd = new ResourceTypeDefinition(resConfigFile);
                    log.debug("Universal Name: " + rtd.getResourceTypeUniversalName());
                    log.debug("Classname: " + rtd.getResourceTypeClassname());
                    hm.put(rtd.getResourceTypeUniversalName(), rtd);
                } else {
                    log.warn("No such file or directory: " + resConfigFile);
                }
            }

            // TODO: This is actually a servlet thing and should be moved there ...
            proxyHostName = props.getProperty("proxy-host-name");
            proxyPort = props.getProperty("proxy-port");
            proxyPrefix = props.getProperty("proxy-prefix");
            log.debug("Proxy Settings: " + proxyHostName + ", " + proxyPort + ", " + proxyPrefix);

            File realmsConfigFile = new File(props.getProperty("realms-config"));
            if (!realmsConfigFile.isAbsolute()) {
                realmsConfigFile = FileUtil.file(propsFile.getParentFile().getAbsolutePath(), realmsConfigFile.toString());
            }
            log.error("DEBUG: Realms Configuration: " + realmsConfigFile);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     *
     */
    public ResourceTypeDefinition getResourceTypeDefinition(String universalName) {
        return (ResourceTypeDefinition) hm.get(universalName);
    }

    /**
     *
     */
    public ResourceTypeDefinition[] getResourceTypeDefinitions() {
        java.util.Set keys = hm.keySet();
        java.util.Iterator keysIterator = keys.iterator();
        ResourceTypeDefinition[] rtds = new ResourceTypeDefinition[keys.size()];
        int i = 0;
        while (keysIterator.hasNext()) {
            String universalName = (String) keysIterator.next();
            rtds[i] = getResourceTypeDefinition(universalName);
            i++;
        }
        return rtds;
    }

    /**
     *
     */
    public Resource newResource(String universalName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
	ResourceTypeDefinition rtd = (ResourceTypeDefinition) hm.get(universalName);
        if (rtd != null) {
            Resource resource = (Resource) Class.forName(rtd.getResourceTypeClassname()).newInstance();
            resource.setRTD(rtd);
            return resource;
        } else {
            log.error("No resource registered for rti: " + universalName);
            return null;
        }
    }

    /**
     *
     */
    public String getConfigurationFile() {
        return CONFIGURATION_FILE;
    }
}
