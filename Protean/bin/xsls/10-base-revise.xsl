<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    xmlns:ditaarch="http://dita.oasis-open.org/architecture/2005/"
    xmlns:dita-ot="http://dita-ot.sourceforge.net/ns/201007/dita-ot"
    exclude-result-prefixes="xs ast ditaarch dita-ot"
    version="2.0">

	<xsl:variable name="job" select="document(resolve-uri('.job.xml', base-uri(.)))" as="document-node()?"/>
	<xsl:variable name="map" select="substring-before(ast:getFile($job/job/property[@name='InputMapDir']/string, '\\'), '.ditamap')"/>
	<xsl:variable name="temp" select="concat(replace($job/job/property[@name='outputdir']/string, '\\', '/'), '/', $map, '_temp')"/>
	<xsl:variable name="files" select="$job/job/files/file[@format='dita']"/>

	<xsl:variable name="LV_name" select="document(concat(ast:getPath(base-uri(), '/'), '/ref-raw.xml'))/root/listitem[1]/item[1]/@LV_name"/>
	<xsl:variable name="Revised" select="document(concat(ast:getPath(base-uri(), '/'), '/../_pvdb/Revised_Base_', $LV_name, '.xml'))" as="document-node()"/>
	<xsl:variable name="TR" select="document(concat(ast:getPath(base-uri(), '/'), '/../_translate/TR_Paragraph_Variable_', $LV_name, '.xml'))" as="document-node()"/>
	<xsl:variable name="Combined">
		<xsl:text>&#xA;</xsl:text>
		<root>
			<xsl:for-each select="$Revised/root/paragraph">
				<xsl:apply-templates select="." mode="combine"/>
			</xsl:for-each>
			<xsl:for-each select="$TR/root/paragraph">
				<xsl:apply-templates select="." mode="combine"/>
			</xsl:for-each>
			<xsl:text>&#xA;</xsl:text>
		</root>
	</xsl:variable>
	<xsl:variable name="Paras" select="$Combined/root/paragraph"/>
	<xsl:variable name="limit" select="count($Paras)"/>

  	<xsl:output method="xml" encoding="UTF-8" indent="no"/>
    <xsl:preserve-space elements="*"/>

	<xsl:template match="paragraph" mode="combine">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:element name="{local-name()}">
			<xsl:apply-templates select="@* except @rev" mode="combine"/>
			<xsl:apply-templates mode="combine"/>
		</xsl:element>
	</xsl:template>

	<xsl:template match="@* | node()" mode="combine">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" mode="combine"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="/">
		<xsl:result-document method="xml" href="combined.xml">
			<xsl:copy-of select="$Combined"/>
		</xsl:result-document>
		<xsl:for-each select="$files">
			<xsl:variable name="filename" select="@result"/>
			<xsl:result-document method="xml" href="{$filename}">
				<xsl:apply-templates select="document(concat('file:/', $temp, '/', @uri))" mode="clean"/>
			</xsl:result-document>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="concept | task | reference" mode="clean">
		<xsl:variable name="doctype-public" select="preceding-sibling::node()[2]"/>
		<xsl:variable name="doctype-system" select="preceding-sibling::node()[1]"/>
		<xsl:text>&#xA;</xsl:text>
		<xsl:value-of select="concat('&lt;!DOCTYPE ', name(), ' PUBLIC ', '&quot;', $doctype-public, '&quot;', '&#x20;' , '&quot;', $doctype-system, '&quot;&gt;')" disable-output-escaping="yes"/>
		<xsl:text>&#xA;</xsl:text>
		<xsl:variable name="topic">
			<xsl:element name="{local-name()}">
				<xsl:apply-templates select="@* | node()" mode="clean"/>
			</xsl:element>
		</xsl:variable>
		<xsl:apply-templates select="$topic" mode="revise"/>
	</xsl:template>

	<xsl:template match="title | shortdesc | note | cmd | li | p | stepsection[parent::steps-unordered]" mode="revise">
		<xsl:choose>
			<xsl:when test="p | note | ul">
				<xsl:element name="{local-name()}">
					<xsl:apply-templates select="@* | node()" mode="revise"/>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise>
				<xsl:element name="{local-name()}">
					<xsl:apply-templates select="@* except @base" mode="revise"/>
					<xsl:call-template name="getBase">
						<xsl:with-param name="this" select="."/>
					</xsl:call-template>
					<xsl:apply-templates select="node()" mode="revise"/>
				</xsl:element>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="processing-instruction()" mode="revise">
		<xsl:copy/>
	</xsl:template>

	<xsl:template match="next" mode="revise">
		<xsl:text>~~</xsl:text>
	</xsl:template>

	<xsl:template match="*" mode="revise">
		<xsl:element name="{local-name()}">
			<xsl:apply-templates select="@* | node()" mode="revise"/>
		</xsl:element>
	</xsl:template>

	<xsl:template match="@*" mode="revise">
		<xsl:attribute name="{local-name()}">
			<xsl:value-of select="."/>
		</xsl:attribute>
	</xsl:template>

	<xsl:template name="getBase">
		<xsl:param name="this"/>
		<xsl:variable name="para1">
			<para>
				<xsl:for-each select="$this/node()">
					<xsl:choose>
						<xsl:when test="self::text()">
							<xsl:value-of select="replace(replace(., '[&#xA;&#x9;]+', '&#x20;'), '\s+', '&#x20;')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:apply-templates select="." mode="revise"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
			</para>
		</xsl:variable>

		<xsl:variable name="para2">
			<xsl:element name="para">
				<xsl:for-each select="$para1/para/node()">
					<xsl:choose>
						<xsl:when test="self::text() and not(preceding-sibling::node()) and not(following-sibling::node())">
							<xsl:value-of select="replace(replace(., '^\s+', ''), '\s+$', '')"/>
						</xsl:when>
						<xsl:when test="self::text() and not(preceding-sibling::node())">
							<xsl:value-of select="replace(., '^\s+', '')"/>
						</xsl:when>
						<xsl:when test="self::text() and not(following-sibling::node())">
							<xsl:value-of select="replace(., '\s+$', '')"/>
						</xsl:when>
						<xsl:when test="self::text()">
							<xsl:value-of select="replace(., '\s+', '&#x20;')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:apply-templates select="." mode="revise"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
			</xsl:element>
		</xsl:variable>

		<xsl:variable name="Exist">
			<xsl:call-template name="yes_or_no">
				<xsl:with-param name="para" select="$para2"/>
				<xsl:with-param name="i" select="1" />
				<xsl:with-param name="Paras" select="$Paras" />
				<xsl:with-param name="limit" select="$limit" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:choose>
			<xsl:when test="starts-with($Exist, 'yes')">
				<xsl:attribute name="base" select="tokenize($Exist, '\|')[2]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="base" select="$Exist"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="yes_or_no">
		<xsl:param name="para"/>
		<xsl:param name="i"/>
		<xsl:param name="Paras"/>
		<xsl:param name="limit"/>

		<xsl:variable name="tmpPara">
			<para>
				<xsl:apply-templates select="$Paras[$i]/node()" mode="revise"/>
			</para>
		</xsl:variable>

		<xsl:choose>
			<xsl:when test="$i &lt;= $limit and deep-equal($para, $tmpPara)">
				<xsl:value-of select="concat('yes', '|', $Paras[$i]/@id)" />
			</xsl:when>
			<xsl:when test="$i &gt; $limit">
				<xsl:value-of select="'no'" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="yes_or_no">
					<xsl:with-param name="para" select="$para"/>
					<xsl:with-param name="i" select="$i + 1" />
					<xsl:with-param name="Paras" select="$Paras" />
					<xsl:with-param name="limit" select="$limit" />
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="title | shortdesc | note | cmd | li | p | stepsection[parent::steps-unordered]" mode="clean">
		<xsl:choose>
			<xsl:when test="p | note | ul">
				<xsl:element name="{local-name()}">
					<xsl:apply-templates select="@* | node()" mode="clean"/>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise>
				<xsl:element name="{local-name()}">
					<xsl:apply-templates select="@* | node()" mode="clean"/>
				</xsl:element>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="*" mode="clean">
		<xsl:element name="{local-name()}">
			<xsl:apply-templates select="@* | node()" mode="clean"/>
		</xsl:element>
	</xsl:template>

	<xsl:template match="@*" mode="clean">
		<xsl:attribute name="{local-name()}">
			<xsl:value-of select="."/>
		</xsl:attribute>
	</xsl:template>

	<xsl:template match="image" mode="clean">
		<xsl:variable name="href" select="@href"/>
		<xsl:element name="{local-name()}">
			<xsl:attribute name="href" select="concat('../../images/', ast:getFile($job/job//file[@uri=$href][1]/@src, '/'))"/>
			<xsl:apply-templates select="@* except @href" mode="clean"/>
		</xsl:element>
	</xsl:template>

	<xsl:template match="xref" mode="clean">
		<xsl:variable name="href" select="@href"/>
		<xsl:element name="{local-name()}">
			<xsl:attribute name="href" select="concat('../', substring-after($job/job//file[@uri=$href][1]/@src, 'BASIC/'))"/>
		</xsl:element>
	</xsl:template>

	<xsl:template match="processing-instruction()" mode="clean">
		<xsl:choose>
			<xsl:when test="name()='workdir' or name()='workdir-uri'">
			</xsl:when>
			<xsl:when test="name()='path2project' or name()='path2project-uri'">
			</xsl:when>
			<xsl:when test="name()='path2rootmap-uri'">
			</xsl:when>
			<xsl:when test="name()='xml-stylesheet'">
				<xsl:text>&#xA;</xsl:text>
				<xsl:copy/>
			</xsl:when>
			<xsl:when test="name()='doctype-public' or name()='doctype-system'">
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="@ditaarch:DITAArchVersion" mode="clean">
	</xsl:template>

	<xsl:template match="@dita-ot:x | @dita-ot:y" mode="clean">
	</xsl:template>

	<xsl:template match="@placement[.='inline']" mode="clean">
	</xsl:template>

	<xsl:template match="@class | @xtrf | @xtrc | @domains" mode="clean">
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