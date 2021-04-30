﻿<?xml  version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    version="2.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:xs="http://www.w3.org/2001/XMLSchema" 
    xmlns:ast="http://www.astkorea.net/" 
    exclude-result-prefixes="xs ast">
    
    
    <xsl:output omit-xml-declaration="yes" encoding="UTF-8" method="xml" />
    <xsl:strip-space elements="*" />
    <xsl:preserve-space elements="p span a" />

    
    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@*, node()" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="@* | node()" mode="imgStrip">
        <xsl:copy>
            <xsl:apply-templates select="@*, node()" mode="imgStrip" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="/">
        <xsl:variable name="str0">
            <xsl:apply-templates select="." mode="imgStrip" />
        </xsl:variable>

        <xsl:apply-templates select="$str0/node()" />
    </xsl:template>

    <xsl:template match="table" mode="imgStrip">
        <xsl:choose>
            <xsl:when test="count(node()) = 1 and 
                            img">
                <xsl:apply-templates />
            </xsl:when>
            <xsl:otherwise>
                <xsl:copy>
                    <xsl:apply-templates select="@*, node()" />
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>


    <xsl:template match="table">
        <xsl:variable name="cntTd" select="count(tr[matches(@class, 'heading')]/td)" />

        <xsl:variable name="cntTdCols">
            <xsl:variable name="Cols1">
                <xsl:for-each select="tr[matches(@class, 'heading')]/td[@*[matches(name(), 'colspan')]]">
                    <xsl:value-of select="number(@*[matches(name(), 'colspan')])" />
                    <xsl:if test="position() != last()">
                        <xsl:text>, </xsl:text>
                    </xsl:if>
                </xsl:for-each>
            </xsl:variable>
            <xsl:value-of select="max(tokenize($Cols1, ', '))" />
        </xsl:variable>
        
        <xsl:variable name="totalCnt">
            <xsl:choose>
                <xsl:when test="tr[matches(@class, 'heading')][td[not(@*[matches(name(), 'colspan')])]]">
                    <xsl:variable name="cntTdColsEmpty" select="if(string-length($cntTdCols) &lt; 1) then 0 else number($cntTdCols) - 1" />
                    <xsl:value-of select="xs:integer($cntTd + $cntTdColsEmpty)" />
                </xsl:when>

                <xsl:otherwise>
                    <xsl:variable name="Cols1">
                        <xsl:for-each select="tr[matches(@class, 'heading')]/td[@*[matches(name(), 'colspan')]]">
                            <CNT>
                                <xsl:sequence select="xs:integer(@*[matches(name(), 'colspan')])" />
                            </CNT>
                        </xsl:for-each>
                    </xsl:variable>
                    <xsl:value-of select="sum($Cols1/CNT)" />
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        
        <xsl:copy>
            <xsl:apply-templates select="@*" />
            <colgroup>
                <xsl:for-each select="1 to $totalCnt">
                    <xsl:variable name="idx" select="." />
                    <col class="{concat('c', $totalCnt, '-col', $idx)}" />
                </xsl:for-each>
            </colgroup>

            <xsl:for-each-group select="node()" group-adjacent="boolean(self::tr[matches(@class, 'theading')])">
                <xsl:choose>
                    <xsl:when test="current-grouping-key()">
                        <thead>
                            <xsl:apply-templates select="current-group()" />
                        </thead>
                    </xsl:when>
                    <xsl:otherwise>
                        <tbody>
                            <xsl:apply-templates select="current-group()" />
                        </tbody>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each-group>
        </xsl:copy>
    </xsl:template>

    <xsl:function name="ast:getPath">
        <xsl:param name="str" />
        <xsl:param name="char" />
        <xsl:value-of select="string-join(tokenize($str, $char)[position() ne last()], $char)" />
    </xsl:function>

    <xsl:function name="ast:last">
        <xsl:param name="arg1" />
        <xsl:param name="arg2" />
        <xsl:value-of select="tokenize($arg1, $arg2)[last()]" />
    </xsl:function>
    
</xsl:stylesheet>