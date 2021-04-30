<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:variable name="comment" select="/root/cmt/item/@page" as="xs:integer*"/>
	<xsl:variable name="items" select="/root/cmt/item" as="element()*"/>

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:strip-space elements="*"/>
    <xsl:preserve-space elements="p span"/>

    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="root">
    	<xsl:text>&#xA;</xsl:text>
    	<xsl:copy>
			<xsl:apply-templates/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="toc">
    	<xsl:text>&#xA;&#x9;</xsl:text>
    	<xsl:copy>
			<xsl:apply-templates/>
			<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="cmt">
    </xsl:template>

    <xsl:template match="item">
		<xsl:variable name="curr" select="@page" as="xs:integer"/>
		<xsl:variable name="next" select="following-sibling::item[1]/@page" as="xs:integer*"/>
		<xsl:variable name="pages" select="$comment[. &gt;= $curr][. &lt; $next]"/>

    	<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
    	<xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:text>&#xA;&#x9;&#x9;&#x9;</xsl:text>
            <title>
            	<xsl:apply-templates/>
            </title>
            
            <xsl:choose>
            	<xsl:when test="$next">
            		<xsl:for-each select="$items[@page=$pages]/p">
            			<xsl:apply-templates select=".">
        					<xsl:with-param name="page" select="parent::item/@page"/>
        				</xsl:apply-templates>
	        		</xsl:for-each>
            	</xsl:when>
            	<xsl:otherwise>
            		<xsl:for-each select="$items[@page &gt;= $curr]/p">
            			<xsl:apply-templates select=".">
        					<xsl:with-param name="page" select="parent::item/@page"/>
        				</xsl:apply-templates>
	        		</xsl:for-each>
            	</xsl:otherwise>
            </xsl:choose>

            <xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="p">
    	<xsl:param name="page"/>
    	<xsl:text>&#xA;&#x9;&#x9;&#x9;</xsl:text>
    	<xsl:copy>
    		<xsl:attribute name="page" select="$page"/>
			<xsl:apply-templates />
		</xsl:copy>
    </xsl:template>

</xsl:stylesheet>