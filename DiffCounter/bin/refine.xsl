<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    xmlns:functx="http://www.functx.com"
    exclude-result-prefixes="xs ast functx"
    version="2.0">

	<xsl:variable name="Deleted" select="/Roots/group[@status='Deleted']"/>
	<xsl:variable name="Added" select="/Roots/group[@status='Added']"/>

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:strip-space elements="Roots group"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="@hash">
	</xsl:template>

	<xsl:template match="Roots">
		<xsl:text>&#xA;</xsl:text>
		<Roots>
			<xsl:apply-templates select="@* | node()"/>
			<xsl:text>&#xA;</xsl:text>
		</Roots>
	</xsl:template>

	<xsl:template match="*[parent::Roots][name()!='group']">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="group[@status='Moved']">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
			<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="group[@status='Added']">
		<xsl:variable name="AddSeq" as="xs:string*">
			<xsl:for-each select="*/@hash">
				<xsl:sequence select="."/>
			</xsl:for-each>
		</xsl:variable>
		<xsl:variable name="found">
			<xsl:for-each select="$Deleted">
				<xsl:variable name="n" select="position()"/>
				<z n="{$n}" w="{count(functx:value-intersect($AddSeq, (*/@hash)))}"/>
			</xsl:for-each>
		</xsl:variable>
		<xsl:variable name="idx">
			<xsl:choose>
				<xsl:when test="$found/z[@w[.!=0]=max($found/z/@w)] and max($found/z/@w) &gt; 3">
					<xsl:value-of select="$found/z[@w[.!=0]=max($found/z/@w)]/@n"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>none</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:choose>
			<xsl:when test="$idx != 'none'">
				<xsl:variable name="sub-group">
					<xsl:for-each select="*">
						<xsl:variable name="check" select="functx:is-value-in-sequence(., $Deleted[$idx]/*)"/>
						<xsl:copy>
							<xsl:apply-templates select="@*"/>
							<xsl:choose>
								<xsl:when test="$check = true()">
									<xsl:attribute name="status">Moved</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="status">Added</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:apply-templates select="node()"/>
						</xsl:copy>
					</xsl:for-each>
				</xsl:variable>

				<xsl:for-each-group select="$sub-group/*" group-by="@status">
					<xsl:text>&#xA;&#x9;</xsl:text>
					<group status="{current-grouping-key()}">
						<xsl:apply-templates select="current-group()" mode="sub"/>
						<xsl:text>&#xA;&#x9;</xsl:text>
					</group>
				</xsl:for-each-group>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>&#xA;&#x9;</xsl:text>
				<xsl:copy>
					<xsl:apply-templates select="@* | node()"/>
					<xsl:text>&#xA;&#x9;</xsl:text>
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="group[@status='Deleted']">
		<xsl:variable name="DelSeq" as="xs:string*">
			<xsl:for-each select="*/@hash">
				<xsl:sequence select="."/>
			</xsl:for-each>
		</xsl:variable>
		<xsl:variable name="found">
			<xsl:for-each select="$Added">
				<xsl:variable name="n" select="position()"/>
				<z n="{$n}" w="{count(functx:value-intersect($DelSeq, (*/@hash)))}"/>
			</xsl:for-each>
		</xsl:variable>
		<xsl:variable name="idx">
			<xsl:choose>
				<xsl:when test="$found/z[@w[.!=0]=max($found/z/@w)] and max($found/z/@w) &gt; 3">
					<xsl:value-of select="$found/z[@w[.!=0]=max($found/z/@w)]/@n"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>none</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:choose>
			<xsl:when test="$idx != 'none'">
				<xsl:text>&#xA;&#x9;</xsl:text>
				<xsl:copy>
					<xsl:apply-templates select="@*"/>
					<xsl:for-each select="*">
						<xsl:variable name="check" select="functx:is-value-in-sequence(., $Added[$idx]/*)"/>
						<xsl:if test="$check = false()">
							<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
							<xsl:copy>
								<xsl:apply-templates select="@* | node()"/>
							</xsl:copy>
						</xsl:if>
					</xsl:for-each>
					<xsl:text>&#xA;&#x9;</xsl:text>
				</xsl:copy>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>&#xA;&#x9;</xsl:text>
				<xsl:copy>
					<xsl:apply-templates select="@* | node()"/>
					<xsl:text>&#xA;&#x9;</xsl:text>
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="*[parent::group]">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="*" mode="sub">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="text">
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			<xsl:choose>
				<xsl:when test="preceding-sibling::node()[1][self::text()][ends-with(., '.')] and starts-with(., '&#x20;')">
					<xsl:value-of select="replace(., '^\s', '')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="."/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="text()" priority="10">
		<xsl:choose>
			<xsl:when test="ends-with(., '.') and following-sibling::node()[1][self::text][starts-with(., '&#x20;')]">
				<xsl:value-of select="concat(., '&#x20;')"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="."/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:function name="functx:is-value-in-sequence" as="xs:boolean">
		<xsl:param name="value" as="xs:anyAtomicType?"/>
		<xsl:param name="seq" as="xs:anyAtomicType*"/>
		<xsl:sequence select="$value = $seq"/>
	</xsl:function>

	<xsl:function name="functx:value-intersect" as="xs:anyAtomicType*">
		<xsl:param name="arg1" as="xs:anyAtomicType*"/>
		<xsl:param name="arg2" as="xs:anyAtomicType*"/>
		<xsl:sequence select="distinct-values($arg1[.=$arg2])"/>
	</xsl:function>

</xsl:stylesheet>