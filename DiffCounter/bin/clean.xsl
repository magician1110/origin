<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:deltaxml="http://www.deltaxml.com/ns/well-formed-delta-v1"
	xmlns:dxx="http://www.deltaxml.com/ns/xml-namespaced-attribute" 
	xmlns:dxa="http://www.deltaxml.com/ns/non-namespaced-attribute" 
    xmlns:aid="http://ns.adobe.com/AdobeInDesign/4.0/"
    exclude-result-prefixes="xs deltaxml dxx dxa aid"
    version="2.0">

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:strip-space elements="*"/>

    <xsl:template match="*">
    	<xsl:if test="local-name()='C_SingleStep' and preceding-sibling::node()[1][self::*]">
    		<xsl:text>&#x20;</xsl:text>
    	</xsl:if>
        <xsl:element name="{local-name()}">
            <xsl:apply-templates select="@* | node()"/>
        </xsl:element>
    	<xsl:if test="local-name()='C_SingleStep' and following::node()[1][self::*]">
    		<xsl:text>&#x20;</xsl:text>
    	</xsl:if>
    </xsl:template>

    <xsl:template match="@*">
        <xsl:attribute name="{local-name()}">
            <xsl:value-of select="."/>
        </xsl:attribute>
    </xsl:template>

    <xsl:template match="@deltaxml:deltaV2">
    	<xsl:choose>
    		<xsl:when test="parent::*/parent::Roots">
		        <xsl:attribute name="diff">
		            <xsl:value-of select="."/>
		        </xsl:attribute>
    		</xsl:when>
    		<xsl:when test=". = 'A!=B'">
    		</xsl:when>
    		<xsl:when test=". = 'A=B'">
    		</xsl:when>
    		<xsl:when test=". ='A'">
		        <xsl:attribute name="status">Deleted</xsl:attribute>
    		</xsl:when>
    		<xsl:when test=". ='B'">
		        <xsl:attribute name="status">Added</xsl:attribute>
    		</xsl:when>
    	</xsl:choose>
    </xsl:template>

    <xsl:template match="@deltaxml:version | @deltaxml:content-type">
    </xsl:template>

    <xsl:template match="Roots/deltaxml:attributes" priority="10">
    </xsl:template>

    <xsl:template match="Roots/deltaxml:textGroup"  priority="10">
    </xsl:template>

    <xsl:template match="Roots">
    	<xsl:text>&#xA;</xsl:text>
        <xsl:element name="{local-name()}">
            <xsl:apply-templates/>
            <xsl:text>&#xA;</xsl:text>
        </xsl:element>
    </xsl:template>

    <xsl:template match="deltaxml:attributes">
    	<xsl:attribute name="hash">
    		<xsl:value-of select="dxa:hash/deltaxml:attributeValue[@deltaxml:deltaV2='B']"/>
    	</xsl:attribute>
    </xsl:template>

    <xsl:template match="deltaxml:textGroup">
    	<xsl:choose>
    		<xsl:when test="@deltaxml:deltaV2='A!=B' and count(deltaxml:text) = 2">
    			<xsl:element name="text">
    				<xsl:attribute name="status">Modified</xsl:attribute>
    				<xsl:value-of select="deltaxml:text[@deltaxml:deltaV2='B']"/>
    			</xsl:element>
    		</xsl:when>
    		<xsl:when test="@deltaxml:deltaV2='B'">
	    			<xsl:element name="text">
	    				<xsl:attribute name="status">Added</xsl:attribute>
	    				<xsl:value-of select="deltaxml:text"/>
	    			</xsl:element>
    		</xsl:when>
    		<xsl:when test="@deltaxml:deltaV2='A'">
    			<xsl:element name="text">
    				<xsl:attribute name="status">Deleted</xsl:attribute>
    				<xsl:value-of select="deltaxml:text"/>
    			</xsl:element>
    		</xsl:when>
    		<xsl:otherwise>
    			<xsl:element name="text">
    				<xsl:apply-templates select="@* | node()"/>
    			</xsl:element>
    		</xsl:otherwise>
    	</xsl:choose>
    </xsl:template>

    <xsl:template match="*[parent::Roots]">
    	<xsl:text>&#xA;&#x9;</xsl:text>
        <xsl:element name="{local-name()}">
        	<xsl:choose>
        		<xsl:when test="deltaxml:attributes">
		            <xsl:attribute name="hash" select="@hash"/>
		            <xsl:attribute name="diff" select="@deltaxml:deltaV2"/>
        		</xsl:when>
        		<xsl:otherwise>
		            <xsl:apply-templates select="@hash"/>
		            <xsl:apply-templates select="@* except @hash"/>
        		</xsl:otherwise>
        	</xsl:choose>
            <xsl:apply-templates />
        </xsl:element>
    </xsl:template>

    <xsl:template match="text()">
    	<xsl:value-of select="replace(replace(., '&#xA;', ''), '\s+', '&#x20;')"/>
    </xsl:template>

</xsl:stylesheet>