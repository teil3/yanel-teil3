<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xhtml="http://www.w3.org/1999/xhtml"
  xmlns:y="http://www.wyona.org/yanel/reindex/1.0">

  <xsl:template match="/y:reindex">
    <html>
      <head>
        <title>Re-index repository</title>
      </head>
      <body>
        <h1>Re-index repository</h1>
        <xsl:apply-templates select="y:exception"/>
        <xsl:apply-templates select="y:message"/>

<!--
        <xsl:if test="not(y:exception or y:message)">
-->
          <p>Choose which repository you would like to re-index:</p>
<!--
          <ul>
            <xsl:for-each select="y:repository">
              <li>
                <a href="?repository={@id}"><xsl:value-of select="."/></a></li>
            </xsl:for-each>
          </ul>
-->
<p>

<!-- For example: ...?repository=yanel_data&path=/projects/where-have-all-the-rabbits-gone/footage -->
<form method="get">
          <select name="repository">
            <xsl:for-each select="y:repository">
<!-- TODO: Set selected option based on y:selected-repository/@id -->
              <option value="{@id}"><xsl:value-of select="."/></option>
            </xsl:for-each>
          </select>
  <br/>
<xsl:choose>
  <xsl:when test="y:selected-path">
  Path: <input type="text" name="path" value="{y:selected-path}"/>
  </xsl:when>
  <xsl:otherwise>
  Path: <input type="text" name="path" value="/"/>
  </xsl:otherwise>
</xsl:choose>
  <br/>
  <input type="submit" value="Re-Index"/>
</form>
</p>
<!--
        </xsl:if>
-->
      </body>
    </html>
  </xsl:template>

  <xsl:template match="y:exception">
    <p style="color:red;"><xsl:value-of select="."/></p>
  </xsl:template>

  <xsl:template match="y:message">
    <p style="color:green;"><xsl:value-of select="."/></p>
  </xsl:template>

</xsl:stylesheet>
