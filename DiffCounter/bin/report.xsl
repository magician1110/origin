<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">

    <xsl:output method="text" encoding="UTF-8" indent="no"  omit-xml-declaration="yes"/>

	<xsl:template match="Roots">
		<xsl:variable name="add-n-modify" select="sum(//*/@add) + sum(//*/@mod)"/>
		<xsl:variable name="delete" select="sum(//*/@del)"/>
		<xsl:variable name="move" select="sum(//*/@mov)"/>

		<xsl:text>----------------------</xsl:text>
		<xsl:text>&#xA;기능 추가/변경: </xsl:text>
		<xsl:value-of select="$add-n-modify"/>

		<xsl:text>&#xA;기능 삭제: </xsl:text>
		<xsl:value-of select="$delete"/>

		<xsl:text>&#xA;위치 이동: </xsl:text>
		<xsl:value-of select="$move"/>
		<xsl:text>&#xA;----------------------</xsl:text>

		<xsl:text>&#xA;총 변경점 개수: </xsl:text>
		<xsl:value-of select="sum($add-n-modify + $delete + $move)"/>
		<xsl:text>&#xA;----------------------</xsl:text>
	</xsl:template>

</xsl:stylesheet>