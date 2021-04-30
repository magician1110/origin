<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

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
    	<xsl:text>&#xA;&#x9;</xsl:text>
    	<xsl:copy>
			<xsl:for-each-group select="*" group-starting-with="item[.!='Boxed Placeholder'][following-sibling::*[1][.='Boxed Placeholder']]">
				<xsl:for-each-group select="current-group()" group-starting-with="item[.!='Boxed Placeholder'][preceding-sibling::*[1][.='Boxed Placeholder']]">
					<xsl:choose>
						<xsl:when test="current-group()[.='Boxed Placeholder']">
							<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
							<item page="{current-group()[1]/@page}" range="{current-group()/@page}">
								<xsl:apply-templates select="current-group()[1]/node()"/>
							</item>
						</xsl:when>
						<xsl:otherwise>
							<xsl:apply-templates select="current-group()"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each-group>
			</xsl:for-each-group>
			<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="item">
    	<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
    	<xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="p">
    	<xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
            <xsl:if test="following-sibling::p">
            	<xsl:text>¶</xsl:text>
            </xsl:if>
		</xsl:copy>
    </xsl:template>

</xsl:stylesheet>