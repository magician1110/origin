<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:param name="curdir"/>
   	<xsl:output method="xml" encoding="UTF-8" omit-xml-declaration="yes"/>
	<xsl:strip-space elements="*"/>
	<xsl:preserve-space elements="seg item"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="/">
		<xsl:variable name="mismatched" select="concat('file:///', replace($curdir, '\\', '/'), '/out/18-mismatched.xml')"/>
		<xsl:variable name="matched" select="concat('file:///', replace($curdir, '\\', '/'), '/out/18-matched.xml')"/>
		<dummy/>

		<xsl:result-document method="xml" href="{$mismatched}">
			<root>
				<xsl:apply-templates select="root/listitem[@mismatch]"/>
				<xsl:text>&#xA;</xsl:text>
			</root>
		</xsl:result-document>

		<xsl:result-document method="xml" href="{$matched}">
			<root>
				<xsl:apply-templates select="root/listitem[not(@mismatch)]"/>
				<xsl:text>&#xA;</xsl:text>
			</root>
		</xsl:result-document>

	</xsl:template>

	<xsl:template match="listitem">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
    		<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="item">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="seg">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="ph[ph]">
		<xsl:variable name="ph" select="."/>
		<xsl:for-each select="$ph/node()">
			<xsl:choose>
				<xsl:when test="self::ph">
					<xsl:copy>
			    		<xsl:apply-templates select="$ph/@*"/>
			    		<xsl:apply-templates select="@* | node()"/>
					</xsl:copy>
				</xsl:when>
				<xsl:when test="self::text()">
					<ph>
						<xsl:apply-templates select="$ph/@*"/>
						<xsl:value-of select="."/>
					</ph>
				</xsl:when>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="ph[@rev='OSD_0754']">
		<cmdname>
    		<xsl:apply-templates select="@* | node()"/>
		</cmdname>
	</xsl:template>

	<xsl:template match="cmdname[@rev='OSD_0326']">
		<ph>
    		<xsl:apply-templates select="@* | node()"/>
		</ph>
	</xsl:template>

</xsl:stylesheet>