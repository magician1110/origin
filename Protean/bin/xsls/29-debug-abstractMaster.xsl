<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

  	<xsl:param name="outdir"/>

  	<xsl:variable name="debug" select="ast:getPath(base-uri(.))"/>
	<xsl:variable name="style" select="document('debug-style.xml')" as="document-node()?"/>
	<xsl:variable name="files" select="collection(concat('../../', ast:getFile($debug, '/'), '/out/2_HTML/debugs?select=*.html;recurse=yes'))"/>

    <xsl:output method="xml" encoding="UTF-8" indent="no"/>
    <xsl:strip-space elements="*"/>
    <xsl:preserve-space elements="div li p cmd"/>

	<xsl:template match="/">
		<xsl:variable name="filename" select="concat($outdir, '/debugs-abstract.html')"/>
		<xsl:result-document href="{$filename}">
			<xsl:text>&#xA;</xsl:text>
			<html outdir="{$outdir}">
				<xsl:value-of select="ast:indent(1)" />
				<head>
					<xsl:value-of select="ast:indent(2)" />
					<style type="text/css">
						<xsl:apply-templates select="$style/style/node()"/>
						<xsl:value-of select="ast:indent(2)" />
					</style>
					<xsl:value-of select="ast:indent(1)" />
				</head>
				<xsl:value-of select="ast:indent(1)" />
				<body>
					<xsl:variable name="items">
						<items>
							<xsl:for-each select="$files">
								<p class="{ast:getLVName(document-uri(.), '/')}">
									<xsl:value-of select="document-uri(.)"/>
								</p>
							</xsl:for-each>
						</items>
					</xsl:variable>

					<xsl:for-each-group select="$items/items/p" group-by="@class">
						<xsl:variable name="LVName" select="current-grouping-key()"/>
						<xsl:value-of select="ast:indent(2)" />
						<p class="folder">
							<xsl:value-of select="concat($LVName, '\')"/>
						</p>
						<xsl:for-each select="current-group()">
							<xsl:variable name="file" select="document(text())" as="document-node()?"/>
							<xsl:variable name="filename" select="ast:getFile(text(), '/')"/>
							<xsl:variable name="data-check" select="$file/html/body/p[1]/@data-check"/>
							<xsl:variable name="true_cnt" select="count($file/html/body/table/tr/td/span[@class='true'][.='true'])"/>
							<xsl:variable name="false_cnt" select="count($file/html/body/table/tr/td/span[@class='false'][.='false'])"/>

							<xsl:value-of select="ast:indent(2)" />
							<p data-check="{$data-check}" path="{$file/html/body/p[1]/@path}">
								<xsl:value-of select="ast:indent(3)" />
								<a target="_blank" href="{concat($LVName, '\', $filename)}">
									<xsl:choose>
										<xsl:when test="$data-check = 'info'">
											<xsl:value-of select="concat('help-info items = ', $true_cnt, ', broken = ', $false_cnt)"/>
										</xsl:when>
										<xsl:when test="$data-check = 'index'">
											<xsl:value-of select="concat('index items = ', $true_cnt, ', broken = ', $false_cnt)"/>
										</xsl:when>
										<xsl:when test="$data-check = 'link'">
											<xsl:value-of select="concat('count of hyperlinks = ', $true_cnt, ', broken or external = ', $false_cnt)"/>
										</xsl:when>
										<xsl:when test="$data-check = 'structure'">
											<xsl:value-of select="concat('category items = ', $true_cnt, ', broken = ', $false_cnt)"/>
										</xsl:when>
										<xsl:when test="$data-check = 'trynow'">
											<xsl:value-of select="concat('trynow items = ', $true_cnt, ', broken = ', $false_cnt)"/>
										</xsl:when>
									</xsl:choose>
								</a>
								<xsl:value-of select="ast:indent(2)" />
							</p>
						</xsl:for-each>
					</xsl:for-each-group>
					<xsl:value-of select="ast:indent(1)" />
				</body>
				<xsl:text>&#xA;</xsl:text>
			</html>
		</xsl:result-document>
		<dummy111/>
	</xsl:template>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:function name="ast:indent">
		<xsl:param name="depth" />
		<xsl:text>&#xA;</xsl:text>
		<xsl:for-each select="1 to $depth">
			<xsl:text>&#x9;</xsl:text>
		</xsl:for-each>
	</xsl:function>

	<xsl:function name="ast:getFile">
		<xsl:param name="str"/>
		<xsl:param name="char"/>
		<xsl:value-of select="tokenize($str, $char)[last()]" />
	</xsl:function>

	<xsl:function name="ast:getPath">
		<xsl:param name="str"/>
		<xsl:value-of select="substring-before(substring-after($str, 'file:/'), '/dummy.xml')" />
	</xsl:function>

	<xsl:function name="ast:getLVName">
		<xsl:param name="str"/>
		<xsl:param name="char"/>
		<xsl:value-of select="tokenize($str, $char)[last() - 1]" />
	</xsl:function>

</xsl:stylesheet>