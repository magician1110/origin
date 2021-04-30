<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:ot-placeholder="http://suite-sol.com/namespaces/ot-placeholder"
	xmlns:opentopic="http://www.idiominc.com/opentopic"
    xmlns:ditaarch="http://dita.oasis-open.org/architecture/2005/"
	xmlns:dita-ot="http://dita-ot.sourceforge.net/ns/201007/dita-ot"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ot-placeholder opentopic ditaarch dita-ot ast"
    version="2.0">

	<xsl:variable name="LV_name" select="/dita-merge/map/@otherprops"/>

  	<xsl:output method="xml" encoding="UTF-8" indent="no"/>
    <xsl:strip-space elements="*"/>
    <xsl:preserve-space elements="title shortdesc note cmd li p stepsection"/>

	<xsl:template match="*">
		<xsl:element name="{local-name()}">
			<xsl:apply-templates select="@* | node()"/>
		</xsl:element>
	</xsl:template>

	<xsl:template match="@*">
		<xsl:attribute name="{local-name()}">
			<xsl:value-of select="." />
		</xsl:attribute>
	</xsl:template>

	<xsl:template match="map | colspec">
	</xsl:template>

	<xsl:template match="concept | task | reference | section | abstract | table | tgroup | thead | tbody | row | entry | info | steps | step | substeps | substep | stepsection | result | taskbody | conbody | refbody | ul | ol | steps-unordered">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="title | shortdesc | note | cmd | li | p | stepsection[parent::steps-unordered]">
		<xsl:choose>
			<xsl:when test="p | note">
				<xsl:apply-templates/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="getID"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="getID">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<listitem>
			<xsl:apply-templates select="(@base, @rev)"/>
			<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
			<item LV_name="{$LV_name}">
				<xsl:apply-templates />
			</item>
			<xsl:text>&#xA;&#x9;</xsl:text>
		</listitem>
	</xsl:template>

	<xsl:template match="@class">
	</xsl:template>

	<xsl:template match="@xtrf | @xtrc">
	</xsl:template>

	<xsl:template match="@placement[.='inline']">
	</xsl:template>

	<xsl:template match="xref">
		<xref rev="{@rev}" href="{@ohref}"/>
	</xsl:template>

	<xsl:template match="processing-instruction()">
	</xsl:template>

	<xsl:template match="comment()">
		<xsl:copy />
	</xsl:template>

	<xsl:template match="text()">
		<xsl:choose>
			<xsl:when test="count(parent::*/node()) = 1">
				<xsl:value-of select="replace(replace(., '^\s+', ''), '\s+$', '')"/>
			</xsl:when>
			<xsl:when test="not(preceding-sibling::node())">
				<xsl:value-of select="replace(replace(., '^\s+', ''), '\s+$', '&#x20;')"/>
			</xsl:when>
			<xsl:when test="not(following-sibling::node())">
				<xsl:value-of select="replace(replace(., '^\s+', '&#x20;'), '\s+$', '')"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="replace(., '\s+', '&#x20;')"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="dita-merge">
		<xsl:text>&#xA;</xsl:text>
		<root>
			<xsl:apply-templates/>
			<xsl:text>&#xA;</xsl:text>
		</root>
	</xsl:template>

</xsl:stylesheet>