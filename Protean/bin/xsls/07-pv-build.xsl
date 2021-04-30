<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:variable name="symbol" select="document(concat(ast:getPath(base-uri(), '/'), '/../../_Reference/symbol_mark/symbol_mark.xml'))" as="document-node()"/>
	<xsl:variable name="images_change_name" select="document(concat(ast:getPath(base-uri(), '/'), '/../../_Reference/meta_data/images_change_name.xml'))" as="document-node()"/>
	<xsl:variable name="image_alt_contents" select="document(concat(ast:getPath(base-uri(), '/'), '/../../_Reference/meta_data/image_alt_contents.xml'))" as="document-node()"/>
    <xsl:variable name="LV_names" select="/root/listitem[1]/item"/>

   	<xsl:output method="xml" encoding="UTF-8" standalone="yes" indent="no"/>
    <xsl:strip-space elements="*"/>
    <xsl:preserve-space elements="item"/>

    <xsl:template match="@*">
    	<xsl:copy-of select="."/>
    </xsl:template>

    <xsl:template match="*">
    	<xsl:variable name="sym" select="name()"/>
    	<xsl:choose>
    		<xsl:when test="$symbol/root/symbolitem[@pvtxt = $sym]">
    			<xsl:choose>
    				<xsl:when test="parent::style[@name='Iword'] and count(parent::style/node()) = 1">
    					<cmdname>
    						<xsl:value-of select="$symbol/root/symbolitem[@pvtxt = $sym]/@xmltxt"/>
    					</cmdname>
    				</xsl:when>
    				<xsl:when test="parent::style[@name!='Iword'] and count(parent::style/node()) = 1">
    					<ph outputclass="{if ( ends-with(parent::style/@name, 'no-trans') ) then 'no-trans' else @name}">
    						<xsl:value-of select="$symbol/root/symbolitem[@pvtxt = $sym]/@xmltxt"/>
    					</ph>
    				</xsl:when>
    				<xsl:otherwise>
    					<xsl:value-of select="$symbol/root/symbolitem[@pvtxt = $sym]/@xmltxt"/>
    				</xsl:otherwise>
    			</xsl:choose>
    		</xsl:when>
    		<xsl:otherwise>
		    	<xsl:copy>
		    		<xsl:apply-templates select="@* | node()" />
		    	</xsl:copy>
    		</xsl:otherwise>
    	</xsl:choose>
    </xsl:template>

    <xsl:template match="languagevariable">
		<xsl:variable name="ID" select="ancestor::listitem/item[1]"/>
		<xsl:variable name="rev" select="@name"/>
    	<xsl:choose>
    		<xsl:when test="parent::style[@name='Iword-Left2Right'] and count(parent::style/languagevariable) = 1">
    			<cmdname>
    				<xsl:attribute name="rev" select="$rev"/>
    				<xsl:attribute name="dir">ltr</xsl:attribute>
		    		<xsl:apply-templates/>
    			</cmdname>
    		</xsl:when>
    		<xsl:when test="parent::style and count(parent::style/languagevariable) = 2">
		    	<ph rev="{$rev}">
		    		<xsl:apply-templates/>
		    	</ph>
    		</xsl:when>
    		<xsl:when test="parent::style[starts-with(@name, 'C_TryNow')]">
    			<xsl:variable name="i" select="count(parent::style/preceding-sibling::style[starts-with(@name, 'C_TryNow')]) + 1"/>
		    	<apiname>
		    		<xsl:if test="ends-with(parent::style/@name, '-Invisible')">
		    			<xsl:attribute name="outputclass">invisible</xsl:attribute>
		    		</xsl:if>
					<xsl:attribute name="rev" select="$rev"/>
		    		<xsl:apply-templates/>
		    	</apiname>
    		</xsl:when>
    		<xsl:when test="parent::style[starts-with(@name, 'Iword')]">
		    	<cmdname>
		    		<xsl:if test="ends-with(parent::style/@name, '-index')">
		    			<xsl:attribute name="outputclass">index</xsl:attribute>
		    		</xsl:if>
					<xsl:attribute name="rev" select="$rev"/>
		    		<xsl:apply-templates/>
		    	</cmdname>
    		</xsl:when>
    		<xsl:when test="parent::style[@name='C_Engraved']">
		    	<varname rev="{$rev}">
		    		<xsl:apply-templates/>
		    	</varname>
    		</xsl:when>
    		<xsl:when test="parent::style[@name='C_Important'] and parent::style/text()">
		    	<ph rev="{$rev}">
		    		<xsl:apply-templates/>
		    	</ph>
    		</xsl:when>
    		<xsl:when test="parent::style[@name='C_Important']">
		    	<b rev="{$rev}">
		    		<xsl:apply-templates/>
		    	</b>
    		</xsl:when>
    		<xsl:otherwise>
		    	<ph>
		    		<xsl:if test="parent::style[starts-with(@name, 'C_Font')] and not(parent::style/text())">
		    			<xsl:attribute name="outputclass" select="parent::style/@name"/>
		    		</xsl:if>
		    		<xsl:attribute name="rev" select="$rev"/>
		    		<xsl:apply-templates/>
		    	</ph>
    		</xsl:otherwise>
    	</xsl:choose>
    </xsl:template>

    <xsl:template match="style">
    	<xsl:choose>
    		<xsl:when test="* and not(text())">
    			<xsl:apply-templates/>
    		</xsl:when>
    		<xsl:when test="* and text()">
    			<xsl:choose>
    				<xsl:when test="@name='C_Important'">
		    			<b>
		    				<xsl:apply-templates/>
		    			</b>
    				</xsl:when>
    				<xsl:when test="@name = 'Iword-Left2Right' and languagevariable[@name]">
    					<cmdname rev="{languagevariable/@name}" dir="ltr">
    						<xsl:apply-templates/>
    					</cmdname>
    				</xsl:when>
    				<xsl:when test="@name = 'C_Font-Left2Right'">
    					<ph outputclass="no-trans" dir="ltr">
    						<xsl:apply-templates/>
    					</ph>
    				</xsl:when>
    				<xsl:otherwise>
		    			<ph outputclass="{if ( ends-with(@name, 'no-trans') ) then 'no-trans' else @name}">
		    				<xsl:apply-templates/>
		    			</ph>
    				</xsl:otherwise>
    			</xsl:choose>
    		</xsl:when>
    		<xsl:otherwise>
    			<xsl:choose>
    				<xsl:when test="ends-with(@name, 'no-trans')">
    					<ph outputclass="no-trans">
    						<xsl:apply-templates/>
    					</ph>
    				</xsl:when>
    				<xsl:when test="@name = 'C_Engraved'">
    					<varname>
    						<xsl:apply-templates/>
    					</varname>
    				</xsl:when>
    				<xsl:when test="starts-with(@name, 'Iword')">
    					<cmdname>
    						<xsl:apply-templates/>
    					</cmdname>
    				</xsl:when>
    				<xsl:when test="@name = 'C_Font-Icon'">
    					<varname outputclass="vd-icon">
    						<xsl:apply-templates/>
    					</varname>
    				</xsl:when>
    				<xsl:when test="starts-with(@name, 'C_Font')">
    					<ph>
    						<xsl:attribute name="outputclass" select="@name"/>
    						<xsl:apply-templates/>
    					</ph>
    				</xsl:when>
    				<xsl:when test="@name = 'C_Important'">
    					<b>
    						<xsl:apply-templates/>
    					</b>
    				</xsl:when>
    				<xsl:otherwise>
    					<xsl:apply-templates/>
    				</xsl:otherwise>
    			</xsl:choose>
    		</xsl:otherwise>
    	</xsl:choose>
    </xsl:template>

    <xsl:template match="crossreference">
		<xsl:variable name="ID" select="ancestor::listitem/item[1]"/>
    	<xsl:variable name="href" select="replace(., '\s+', '&#x20;')"/>
    	<xsl:variable name="dita" select="concat(lower-case(replace(replace($href, '\p{P}', ''), '&#x20;', '_')), '.dita')"/>
		<xref href="{$dita}"/>
    </xsl:template>

    <xsl:template match="figure">
    	<xsl:variable name="linkpath" select="replace(@linkpath, '\\', '/')"/>
		<xsl:variable name="ID" select="ancestor::listitem/item[1]"/>
 		<xsl:variable name="href" select="concat('../../images/', substring-before(ast:getFile($linkpath, '/'), '.'), '.')"/>

    	<xsl:choose>
    		<xsl:when test="ends-with(@linkpath, 'next.ai')">
    			<xsl:text>~~</xsl:text>
    		</xsl:when>
    		<xsl:when test="count(parent::item/node()) = 1">
		    	<xsl:variable name="href">
		    		<xsl:call-template name="fix-image-name">
						<xsl:with-param name="ID" select="$ID"/>
		    			<xsl:with-param name="linkpath" select="$linkpath"/>
		    			<xsl:with-param name="href" select="$href"/>
		    		</xsl:call-template>
		    	</xsl:variable>
		    	<xsl:variable name="otherprops">
		    		<xsl:call-template name="get-yes-no">
		    			<xsl:with-param name="ID" select="$ID"/>
		    			<xsl:with-param name="href" select="$href"/>
		    		</xsl:call-template>
		    	</xsl:variable>
		    	<image href="{$href}">
					<xsl:attribute name="placement">break</xsl:attribute>
					<xsl:attribute name="otherprops" select="$otherprops"/>
		    	</image>
    		</xsl:when>
    		<xsl:otherwise>
		    	<xsl:variable name="href">
		    		<xsl:call-template name="fix-image-name">
						<xsl:with-param name="ID" select="$ID"/>
		    			<xsl:with-param name="linkpath" select="$linkpath"/>
		    			<xsl:with-param name="href" select="$href"/>
		    		</xsl:call-template>
		    	</xsl:variable>
		    	<xsl:variable name="otherprops">
		    		<xsl:call-template name="get-yes-no">
		    			<xsl:with-param name="ID" select="$ID"/>
		    			<xsl:with-param name="href" select="$href"/>
		    		</xsl:call-template>
		    	</xsl:variable>
		    	<image href="{$href}" otherprops="{$otherprops}"/>
    		</xsl:otherwise>
    	</xsl:choose>
    </xsl:template>

	<xsl:template name="fix-image-name">
		<xsl:param name="ID"/>
		<xsl:param name="linkpath"/>
		<xsl:param name="href"/>
		<xsl:variable name="filename" select="ast:getFile($linkpath, '/')"/>

		<xsl:choose>
			<xsl:when test="$images_change_name/root/imageitem[@old = $filename]">
				<xsl:value-of select="concat('../../images/', $images_change_name/root/imageitem[@old = $filename]/@after)"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="concat('../../images/', replace($filename, '\.ai', '.svg'))"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="get-yes-no">
		<xsl:param name="ID"/>
		<xsl:param name="href"/>
		<xsl:variable name="image" select="substring-before(ast:getFile($href, '/'), '.')"/>

		<xsl:choose>
			<xsl:when test="$image_alt_contents/root/alt[@image = $image]">
				<xsl:text>alt:yes</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>alt:no</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

    <xsl:template match="text()">
    	<xsl:value-of select="replace(., '\s+', '&#x20;')"/>
    </xsl:template>

    <xsl:template match="root">
    	<xsl:text>&#xA;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
			<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="listitem[1]">
    </xsl:template>

    <xsl:template match="listitem">
    	<xsl:text>&#xA;&#x9;</xsl:text>
    	<xsl:copy>
    	    <xsl:attribute name="ID" select="item[1]"/>
			<xsl:apply-templates select="@* | node()" />
    		<xsl:text>&#xA;&#x9;</xsl:text>
    	</xsl:copy>
    </xsl:template>

    <xsl:template match="item[1]">
    </xsl:template>

    <xsl:template match="item">
    	<xsl:variable name="i" select="count(preceding-sibling::item) + 1"/>
    	<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
    	<xsl:copy>
    		<xsl:attribute name="LV_name" select="$LV_names[$i]"/>
    		<xsl:apply-templates select="node()" />
    	</xsl:copy>
    </xsl:template>

	<xsl:function name="ast:getPath">
		<xsl:param name="str"/>
		<xsl:param name="char"/>
		<xsl:value-of select="string-join(tokenize($str, $char)[position() ne last()], '/')" />
	</xsl:function>

	<xsl:function name="ast:getFile">
		<xsl:param name="str"/>
		<xsl:param name="char"/>
		<xsl:value-of select="tokenize($str, $char)[last()]" />
	</xsl:function>

</xsl:stylesheet>