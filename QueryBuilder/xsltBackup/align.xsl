<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:variable name="comment" select="/root/cmt/item/@page" as="xs:integer*"/>
    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:strip-space elements="*"/>

    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="root">
    	<xsl:text>&#xA;</xsl:text>
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
            <xsl:text>&#xA;</xsl:text>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="toc">
    	<xsl:text>&#xA;&#x9;</xsl:text>
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
            <xsl:text>&#xA;&#x9;</xsl:text>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="toc/item">
		<xsl:variable name="curr" select="@page" as="xs:integer"/>
		<xsl:variable name="next" select="following-sibling::item[1]/@page" as="xs:integer*"/>

    	<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
        <xsl:copy>
            <xsl:apply-templates select="@level"/>
            <xsl:text>&#xA;&#x9;&#x9;&#x9;</xsl:text>
            <xsl:element name="title">
            	<xsl:apply-templates/>
            </xsl:element>
            <xsl:choose>
	            <xsl:when test="empty($next) and $comment[. &gt;= $curr][1]">
	            	<xsl:variable name="page" select="$comment[. &gt;= $curr][1]"/>
	            	<xsl:text>&#xA;&#x9;&#x9;&#x9;</xsl:text>
		            <xsl:apply-templates select="/root/cmt/item[@page=$page]/p">
		            	<xsl:with-param name="page" select="concat(tokenize(/root/cmt/item[@page=$page]/@range)[1], '~', tokenize(/root/cmt/item[@page=$page]/@range)[last()])"/>
		            </xsl:apply-templates>
	            </xsl:when>
	            <xsl:when test="$comment[. &gt;= $curr][. &lt; $next][1]">
	            	<xsl:variable name="page" select="$comment[. &gt;= $curr][. &lt; $next][1]"/>
	            	<xsl:text>&#xA;&#x9;&#x9;&#x9;</xsl:text>
		            <xsl:apply-templates select="/root/cmt/item[@page=$page]/p">
		            	<xsl:with-param name="page" select="$page"/>
		            </xsl:apply-templates>
	            </xsl:when>
            </xsl:choose>
            <xsl:text>&#xA;&#x9;&#x9;</xsl:text>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="root/cmt">
    </xsl:template>

    <xsl:template match="p">
    	<xsl:param name="page"/>
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:value-of select="concat('p.', $page, '¶')"/>
            <xsl:apply-templates select="node()"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="@style">
    	<xsl:choose>
    		<xsl:when test="contains(., 'font-weight:bold')">
    			<xsl:attribute name="style">bold</xsl:attribute>
    		</xsl:when>
    		<xsl:otherwise>
    		</xsl:otherwise>
    	</xsl:choose>
    </xsl:template>

</xsl:stylesheet>