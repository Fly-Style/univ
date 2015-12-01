<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" >
    <xsl:template match="/">
        <html>
            <body>
                <p> Name : <xsl:value-of select="device/computer/Name_"/>
                </p>
                <div style="margin-left:20px">
                    <p>
                        Origin: <xsl:value-of select="device/computer/Origin"/>
                    </p>
                    <p> Price: <xsl:value-of select="device/computer/Price"/> </p>

                    <p> Type:</p>
                    <div style="margin-left:20px">
                        <p>
                            Port: <xsl:value-of select="computer/tipe/Port"/>
                        </p>
                        <p>Devices: </p>

                            <p>
                            Keyboard: <xsl:value-of select="computer/tipe/Devices/Keyboard"/>
                            </p>
                            <p>
                            Mouse: <xsl:value-of select="computer/tipe/Devices/Mouse"/>
                            </p>
                            <p>
                            Headphones: <xsl:value-of select="computer/tipe/Devices/Headphones"/>
                            </p>

                        <p>
                            Energy Using: <xsl:value-of select="computer/tipe/Energy"/> watt
                        </p>
                        <p>
                            Cooler : <xsl:value-of select="computer/tipe/IsCooler"/>
                        </p>

                        <p>
                            Critical : <xsl:value-of select="computer/tipe/Crit" />
                        </p>

                    </div>
                </div>

            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>