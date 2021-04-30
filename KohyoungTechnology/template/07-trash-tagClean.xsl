<?xml  version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    version="2.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:xs="http://www.w3.org/2001/XMLSchema" 
    xmlns:ast="http://www.astkorea.net/" 
    exclude-result-prefixes="xs ast">
    
    
    <xsl:output omit-xml-declaration="yes" encoding="UTF-8" method="xml" />
    <xsl:strip-space elements="*" />
    <xsl:preserve-space elements="p span a h1 h2 h3 h4 h5 b i" />

    <xsl:variable name="lang" select="tokenize(replace(root/@sourcename, '\.html?', ''), '_')[last()]" />
    
    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@*, node()" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="span">
        <xsl:choose>
            <xsl:when test="not(string(normalize-space(.))) and not(*)">
                <xsl:text>&#xfeff;</xsl:text>
            </xsl:when>

            <xsl:when test="matches(., '^[.,\(\)]')">
                <xsl:text></xsl:text>
            </xsl:when>
        </xsl:choose>
        
        <xsl:apply-templates select="node()" />
    </xsl:template>

    <xsl:template match="p">
        <xsl:choose>
            <xsl:when test="count(node()) = 1 and 
                            *[matches(name(), '(span|b|i)')][matches(normalize-space(.), '^৺$')]">
            </xsl:when>

            <xsl:when test="preceding-sibling::*[1]/*[matches(normalize-space(.), '^৺$')]">
                <xsl:variable name="class" select="@*[matches(name(), 'class')]" />
                
                <xsl:copy>
                    <xsl:apply-templates select="@* except @class" />
                    <xsl:attribute name="class" select="$class" />
                    <xsl:apply-templates select="node()" />
                </xsl:copy>
            </xsl:when>

            <xsl:when test="*[1][name()='table']">
                <xsl:apply-templates />
            </xsl:when>
            
            <xsl:otherwise>
                <xsl:copy>
                    <xsl:apply-templates select="@*, node()" />
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="b">
        <xsl:choose>
            <xsl:when test="matches(., '^ $')">
                <xsl:text>&#x20;</xsl:text>
            </xsl:when>
            
            <xsl:when test="not(string(normalize-space(.))) and not(*)">
            </xsl:when>

            <xsl:when test="count(node()) = 1 and 
                            count(span/node()) = 1 and span/img">
                <xsl:apply-templates />
            </xsl:when>
            
            <xsl:otherwise>
                <xsl:copy>
                    <xsl:apply-templates select="@*, node()" />
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="img">
        <xsl:choose>
            <xsl:when test="ancestor::p[not(preceding-sibling::node())]/parent::div[not(preceding-sibling::node())]/parent::body">
                <xsl:copy>
                    <xsl:apply-templates select="@*, node()" />
                </xsl:copy>
            </xsl:when>
            
            <xsl:when test="parent::*/@*[matches(name(), 'z-index')]">
            </xsl:when>
            
            <xsl:otherwise>
                <xsl:copy>
                    <xsl:apply-templates select="@*, node()" />
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="text()"  priority="10">
        <xsl:value-of select="replace(replace(replace(.,'\s+', '&#x20;'), '&#xa0;', ' '), '[৺]+', '&#x20;')" />
    </xsl:template>

    <xsl:template match="@*[not(matches(name(), '(outFolderName|class|background|font|color|align|href|name|src|colspan|rowspan)'))]">
    </xsl:template>

    <xsl:template match="a[matches(@name, '^_Toc')]">
        <xsl:apply-templates />
    </xsl:template>


    <xsl:template match="*/@*[matches(name(), 'ast\d+\-class')]">
        <xsl:attribute name="class" select="." />
    </xsl:template>

    <xsl:template match="br" />

    <xsl:template match="head | style | html | div[matches(@class, 'WordSection')]">
        <xsl:apply-templates />
    </xsl:template>

    <xsl:template match="body">
        <xsl:copy>
            <xsl:attribute name="lang" select="$lang" />
            <xsl:apply-templates select="@*, node()" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="tr[matches(name(@*), 'height')]">
        <xsl:choose>
            <xsl:when test="@*[matches(name(), 'height')] = '0'">
            </xsl:when>

            <xsl:otherwise>
                <xsl:copy>
                    <xsl:apply-templates select="@*, node()" />
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="head/style/comment()">
        <xsl:result-document href="07-extract-styleInfo.xml">
            <root>
                <xsl:analyze-string select="." regex="(\}})(\s+)?(\w+)([\w+\s+\.,\-]+)(\{{mso-style-link)(.*?)(;)">
                    <xsl:matching-substring>
                        <stylelist>
                            <xsl:value-of select="regex-group(0)" />
                        </stylelist>
                    </xsl:matching-substring>
                    <xsl:non-matching-substring>
                        
                        <xsl:analyze-string select="." regex="(\}})(\s+)?(\w+)([\w+\s+\.,\-]+)(\{{mso-style-name)(.*?)(;)">
                            <xsl:matching-substring>
                                <stylelist>
                                    <xsl:value-of select="regex-group(0)" />
                                </stylelist>
                            </xsl:matching-substring>
                            <xsl:non-matching-substring>
                            </xsl:non-matching-substring>
                        </xsl:analyze-string>
                    </xsl:non-matching-substring>
                </xsl:analyze-string>
            </root>
        </xsl:result-document>
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