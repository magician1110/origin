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

	<xsl:template match="/">
		<xsl:variable name="filename" select="concat('file:///', replace($curdir, '\\', '/'), '/out/23-clued-foreign.xml')"/>
		<dummy/>
		<xsl:result-document method="xml" href="{$filename}">
			<root>
				<xsl:apply-templates select="root/listitem"/>
				<xsl:text>&#xA;</xsl:text>
			</root>
		</xsl:result-document>
	</xsl:template>

	<xsl:template match="@* | node()">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="listitem">
		<xsl:variable name="basic" select="item[matches(@LV_name, '^(ENG|ENG-US|KOR)$')]"/>
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
    		<xsl:apply-templates select="@*"/>
    		<xsl:for-each select="item">
    			<xsl:choose>
    				<xsl:when test="matches(@LV_name, '^(ENG|ENG-US|KOR)$')">
    					<xsl:apply-templates select="." mode="basic"/>
    				</xsl:when>
    				<xsl:otherwise>
						<xsl:apply-templates select="." mode="foreign">
							<xsl:with-param name="basic" select="$basic"/>
						</xsl:apply-templates>
    				</xsl:otherwise>
    			</xsl:choose>
    		</xsl:for-each>
    		<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="item" mode="basic">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
		    <xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="item" mode="foreign">
		<xsl:param name="basic"/>
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
		    <xsl:apply-templates select="@*"/>
		    <xsl:for-each select="node()">
		    	<xsl:choose>
		    		<xsl:when test="self::*[name() != 'seg']">
		    			<xsl:variable name="id" select="generate-id()"/>
		    			<xsl:variable name="elmName" select="name()"/>
		    			<xsl:variable name="attClue" select="ast:getAttClue(@*)"/>
		    			<xsl:variable name="elmValue" select="."/>
			    			<xsl:copy>
					    		<xsl:apply-templates select="@* except @x"/>
					    		<xsl:variable name="candidate" as="element()*">
									<xsl:for-each select="$basic/*">
										<xsl:for-each select="self::*[name() = $elmName]">
							    			<xsl:variable name="findAttClue" select="ast:getAttClue(@*)"/>
							    			<xsl:for-each select="self::*[$findAttClue = $attClue]">
							    				<xsl:sequence select="."/>
							    			</xsl:for-each>
										</xsl:for-each>
									</xsl:for-each>
					    		</xsl:variable>
								<xsl:variable name="i" select="count(parent::item/*[name()=$elmName][generate-id()=$id]/preceding-sibling::*[name()=$elmName][ast:getAttClue(@*) = $attClue]) + 1"/>
					    		<xsl:attribute name="x">
					    			<xsl:choose>
					    				<xsl:when test="count(parent::item/*) = 1">1</xsl:when>
					    				<xsl:when test="@*[name()!='x']">
					    					<xsl:value-of select="$candidate[$i]/@x "/>
					    				</xsl:when>
					    				<xsl:when test="$elmName = 'b' and count(parent::item/b) = 1">
					    					<xsl:value-of select="$basic/*[name()='b']/@x"/>
					    				</xsl:when>
					    				<xsl:when test="$elmName = 'b' and count(parent::item/b[not(@*[name()!='b'][name()!='x'])]) = 1">
					    					<xsl:value-of select="$basic/b[not(@*[name()!='b'][name()!='x'])]/@x"/>
					    				</xsl:when>
					    				<xsl:when test="matches($elmName, '^(b|next)$')">
					    					<xsl:value-of select="count(preceding-sibling::*) + 1"/>
					    				</xsl:when>
					    				<xsl:when test="$basic/*[.=$elmValue]">
					    					<xsl:value-of select="$basic/*[.=$elmValue]/@x"/>
					    				</xsl:when>
					    				<xsl:otherwise></xsl:otherwise>
					    			</xsl:choose>
					    		</xsl:attribute>
					    		<xsl:apply-templates select="node()"/>
						</xsl:copy>
		    		</xsl:when>
		    		<xsl:otherwise>
		    			<xsl:apply-templates select="."/>
		    		</xsl:otherwise>
		    	</xsl:choose>
		    </xsl:for-each>
		</xsl:copy>
	</xsl:template>

	<xsl:function name="ast:getAttClue">
		<xsl:param name="atts"/>
		<xsl:for-each select="$atts">
			<xsl:if test="name() != 'x'">
				<xsl:value-of select="concat(name(), .)"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:function>

</xsl:stylesheet>