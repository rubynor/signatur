package no.rubynor.signatur.provider.bankid.constants;

public final class ExampleData {

    // This is just example data. A real application would get these from elsewhere, e.g. a database
    public static final String TEXT_TO_SIGN = "Dette er en fin tekst som signeres som ISO-8859-1 (Latin1)";

    public static final String XML_TO_SIGN = "<Person>" + "	<Name>Ola Normann</Name>" + "	<Street>Olaveien 99</Street>"
            + "	<Zip>9988</Zip>" + "	<City>Oslo</City>" + "</Person>";

    public static final String SIGN_XSL = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"
            + "<xsl:stylesheet version=\"2.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:fo=\"http://www.w3.org/1999/XSL/Format\" "
            + "xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:fn=\"http://www.w3.org/2005/02/xpath-functions\" "
            + "xmlns:xdt=\"http://www.w3.org/2005/02/xpath-datatypes\"> "
            + "<xsl:template match=\"/\">"
            + "<HTML>"
            + "<BODY>"
            + "<xsl:for-each select=\"Person\">"
            + "<table><tr><td><i><xsl:value-of select=\"Name\"/><br/>"
            + "<xsl:value-of select=\"Street\"/><br/>"
            + "<xsl:value-of select=\"Zip\"/> <xsl:value-of select=\"City\"/></i></td></tr>"
            + "<tr><td align=\"right\"> 24. desember, Oslo</td></tr>"
            + "<tr><td><h2>Eksempel på XML/XSL</h2></td></tr>"
            + "<tr><td>XML transformert med XSL er veldig godt egnet til å generere dokumenter "
            + " basert på data f.eks. fra en database. <p/> "
            + "Med vennelig hilsen<br/>"
            + "Kari Nordmann</td></tr></table>"
            + "</xsl:for-each>"
            + "</BODY>"
            + "</HTML>"
            + "</xsl:template>"
            + "</xsl:stylesheet>";

    public static final byte[] PDF_TO_SIGN = new byte[]{(byte) 0x25, (byte) 0x50, (byte) 0x44, (byte) 0x46, (byte) 0x2d, (byte) 0x31,
            (byte) 0x2e, (byte) 0x32, (byte) 0x0a, (byte) 0x25, (byte) 0xc7, (byte) 0xec, (byte) 0x8f, (byte) 0xa2, (byte) 0x0a, (byte) 0x36,
            (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x3c, (byte) 0x3c, (byte) 0x2f,
            (byte) 0x4c, (byte) 0x65, (byte) 0x6e, (byte) 0x67, (byte) 0x74, (byte) 0x68, (byte) 0x20, (byte) 0x37, (byte) 0x20, (byte) 0x30,
            (byte) 0x20, (byte) 0x52, (byte) 0x2f, (byte) 0x46, (byte) 0x69, (byte) 0x6c, (byte) 0x74, (byte) 0x65, (byte) 0x72, (byte) 0x20,
            (byte) 0x2f, (byte) 0x46, (byte) 0x6c, (byte) 0x61, (byte) 0x74, (byte) 0x65, (byte) 0x44, (byte) 0x65, (byte) 0x63, (byte) 0x6f,
            (byte) 0x64, (byte) 0x65, (byte) 0x3e, (byte) 0x3e, (byte) 0x0a, (byte) 0x73, (byte) 0x74, (byte) 0x72, (byte) 0x65, (byte) 0x61,
            (byte) 0x6d, (byte) 0x0a, (byte) 0x78, (byte) 0x9c, (byte) 0x2b, (byte) 0x54, (byte) 0x30, (byte) 0xd0, (byte) 0x33, (byte) 0x54,
            (byte) 0x30, (byte) 0x00, (byte) 0x41, (byte) 0x28, (byte) 0x9d, (byte) 0x9c, (byte) 0xcb, (byte) 0x55, (byte) 0xc8, (byte) 0xa5,
            (byte) 0x1f, (byte) 0x64, (byte) 0xa2, (byte) 0x90, (byte) 0x5e, (byte) 0xcc, (byte) 0x55, (byte) 0xa8, (byte) 0x60, (byte) 0x08,
            (byte) 0x96, (byte) 0x82, (byte) 0x51, (byte) 0xc9, (byte) 0xb9, (byte) 0x0a, (byte) 0x4e, (byte) 0x21, (byte) 0x40, (byte) 0x49,
            (byte) 0x4b, (byte) 0x05, (byte) 0x43, (byte) 0x0b, (byte) 0x85, (byte) 0x90, (byte) 0x34, (byte) 0x2e, (byte) 0x88, (byte) 0x0e,
            (byte) 0x43, (byte) 0x05, (byte) 0x23, (byte) 0x03, (byte) 0x03, (byte) 0x05, (byte) 0x53, (byte) 0x20, (byte) 0x0e, (byte) 0xc9,
            (byte) 0xe5, (byte) 0xd2, (byte) 0x70, (byte) 0x49, (byte) 0x2d, (byte) 0x29, (byte) 0x49, (byte) 0x55, (byte) 0x48, (byte) 0x2d,
            (byte) 0x52, (byte) 0x48, (byte) 0xcd, (byte) 0x53, (byte) 0x08, (byte) 0x70, (byte) 0x71, (byte) 0x53, (byte) 0x28, (byte) 0xce,
            (byte) 0xcf, (byte) 0x55, (byte) 0x28, (byte) 0xce, (byte) 0x4e, (byte) 0xcc, (byte) 0x51, (byte) 0x28, (byte) 0xce, (byte) 0x4c,
            (byte) 0xcf, (byte) 0x4b, (byte) 0x2d, (byte) 0x4a, (byte) 0x2d, (byte) 0xd6, (byte) 0x0c, (byte) 0xc9, (byte) 0xe2, (byte) 0x72,
            (byte) 0x0d, (byte) 0x51, (byte) 0x08, (byte) 0xe4, (byte) 0x02, (byte) 0x41, (byte) 0x00, (byte) 0x0b, (byte) 0xa0, (byte) 0x1e,
            (byte) 0xb3, (byte) 0x65, (byte) 0x6e, (byte) 0x64, (byte) 0x73, (byte) 0x74, (byte) 0x72, (byte) 0x65, (byte) 0x61, (byte) 0x6d,
            (byte) 0x0a, (byte) 0x65, (byte) 0x6e, (byte) 0x64, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x37, (byte) 0x20,
            (byte) 0x30, (byte) 0x20, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x31, (byte) 0x30, (byte) 0x39, (byte) 0x0a,
            (byte) 0x65, (byte) 0x6e, (byte) 0x64, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x35, (byte) 0x20, (byte) 0x30,
            (byte) 0x20, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x3c, (byte) 0x3c, (byte) 0x2f, (byte) 0x54, (byte) 0x79,
            (byte) 0x70, (byte) 0x65, (byte) 0x2f, (byte) 0x50, (byte) 0x61, (byte) 0x67, (byte) 0x65, (byte) 0x2f, (byte) 0x4d, (byte) 0x65,
            (byte) 0x64, (byte) 0x69, (byte) 0x61, (byte) 0x42, (byte) 0x6f, (byte) 0x78, (byte) 0x20, (byte) 0x5b, (byte) 0x30, (byte) 0x20,
            (byte) 0x30, (byte) 0x20, (byte) 0x36, (byte) 0x31, (byte) 0x32, (byte) 0x20, (byte) 0x37, (byte) 0x39, (byte) 0x32, (byte) 0x5d,
            (byte) 0x0a, (byte) 0x2f, (byte) 0x52, (byte) 0x6f, (byte) 0x74, (byte) 0x61, (byte) 0x74, (byte) 0x65, (byte) 0x20, (byte) 0x30,
            (byte) 0x2f, (byte) 0x50, (byte) 0x61, (byte) 0x72, (byte) 0x65, (byte) 0x6e, (byte) 0x74, (byte) 0x20, (byte) 0x33, (byte) 0x20,
            (byte) 0x30, (byte) 0x20, (byte) 0x52, (byte) 0x0a, (byte) 0x2f, (byte) 0x52, (byte) 0x65, (byte) 0x73, (byte) 0x6f, (byte) 0x75,
            (byte) 0x72, (byte) 0x63, (byte) 0x65, (byte) 0x73, (byte) 0x3c, (byte) 0x3c, (byte) 0x2f, (byte) 0x50, (byte) 0x72, (byte) 0x6f,
            (byte) 0x63, (byte) 0x53, (byte) 0x65, (byte) 0x74, (byte) 0x5b, (byte) 0x2f, (byte) 0x50, (byte) 0x44, (byte) 0x46, (byte) 0x20,
            (byte) 0x2f, (byte) 0x54, (byte) 0x65, (byte) 0x78, (byte) 0x74, (byte) 0x5d, (byte) 0x0a, (byte) 0x2f, (byte) 0x45, (byte) 0x78,
            (byte) 0x74, (byte) 0x47, (byte) 0x53, (byte) 0x74, (byte) 0x61, (byte) 0x74, (byte) 0x65, (byte) 0x20, (byte) 0x31, (byte) 0x30,
            (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x52, (byte) 0x0a, (byte) 0x2f, (byte) 0x46, (byte) 0x6f, (byte) 0x6e, (byte) 0x74,
            (byte) 0x20, (byte) 0x31, (byte) 0x31, (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x52, (byte) 0x0a, (byte) 0x3e, (byte) 0x3e,
            (byte) 0x0a, (byte) 0x2f, (byte) 0x43, (byte) 0x6f, (byte) 0x6e, (byte) 0x74, (byte) 0x65, (byte) 0x6e, (byte) 0x74, (byte) 0x73,
            (byte) 0x20, (byte) 0x36, (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x52, (byte) 0x0a, (byte) 0x3e, (byte) 0x3e, (byte) 0x0a,
            (byte) 0x65, (byte) 0x6e, (byte) 0x64, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x33, (byte) 0x20, (byte) 0x30,
            (byte) 0x20, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x3c, (byte) 0x3c, (byte) 0x20, (byte) 0x2f, (byte) 0x54,
            (byte) 0x79, (byte) 0x70, (byte) 0x65, (byte) 0x20, (byte) 0x2f, (byte) 0x50, (byte) 0x61, (byte) 0x67, (byte) 0x65, (byte) 0x73,
            (byte) 0x20, (byte) 0x2f, (byte) 0x4b, (byte) 0x69, (byte) 0x64, (byte) 0x73, (byte) 0x20, (byte) 0x5b, (byte) 0x0a, (byte) 0x35,
            (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x52, (byte) 0x0a, (byte) 0x5d, (byte) 0x20, (byte) 0x2f, (byte) 0x43, (byte) 0x6f,
            (byte) 0x75, (byte) 0x6e, (byte) 0x74, (byte) 0x20, (byte) 0x31, (byte) 0x0a, (byte) 0x3e, (byte) 0x3e, (byte) 0x0a, (byte) 0x65,
            (byte) 0x6e, (byte) 0x64, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x31, (byte) 0x20, (byte) 0x30, (byte) 0x20,
            (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x3c, (byte) 0x3c, (byte) 0x2f, (byte) 0x54, (byte) 0x79, (byte) 0x70,
            (byte) 0x65, (byte) 0x20, (byte) 0x2f, (byte) 0x43, (byte) 0x61, (byte) 0x74, (byte) 0x61, (byte) 0x6c, (byte) 0x6f, (byte) 0x67,
            (byte) 0x20, (byte) 0x2f, (byte) 0x50, (byte) 0x61, (byte) 0x67, (byte) 0x65, (byte) 0x73, (byte) 0x20, (byte) 0x33, (byte) 0x20,
            (byte) 0x30, (byte) 0x20, (byte) 0x52, (byte) 0x0a, (byte) 0x3e, (byte) 0x3e, (byte) 0x0a, (byte) 0x65, (byte) 0x6e, (byte) 0x64,
            (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x34, (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x6f, (byte) 0x62,
            (byte) 0x6a, (byte) 0x0a, (byte) 0x3c, (byte) 0x3c, (byte) 0x2f, (byte) 0x54, (byte) 0x79, (byte) 0x70, (byte) 0x65, (byte) 0x2f,
            (byte) 0x45, (byte) 0x78, (byte) 0x74, (byte) 0x47, (byte) 0x53, (byte) 0x74, (byte) 0x61, (byte) 0x74, (byte) 0x65, (byte) 0x2f,
            (byte) 0x4e, (byte) 0x61, (byte) 0x6d, (byte) 0x65, (byte) 0x2f, (byte) 0x52, (byte) 0x34, (byte) 0x2f, (byte) 0x54, (byte) 0x52,
            (byte) 0x2f, (byte) 0x49, (byte) 0x64, (byte) 0x65, (byte) 0x6e, (byte) 0x74, (byte) 0x69, (byte) 0x74, (byte) 0x79, (byte) 0x3e,
            (byte) 0x3e, (byte) 0x0a, (byte) 0x65, (byte) 0x6e, (byte) 0x64, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x31,
            (byte) 0x30, (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x3c, (byte) 0x3c,
            (byte) 0x2f, (byte) 0x52, (byte) 0x34, (byte) 0x0a, (byte) 0x34, (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x52, (byte) 0x3e,
            (byte) 0x3e, (byte) 0x0a, (byte) 0x65, (byte) 0x6e, (byte) 0x64, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x31,
            (byte) 0x31, (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x3c, (byte) 0x3c,
            (byte) 0x2f, (byte) 0x52, (byte) 0x39, (byte) 0x0a, (byte) 0x39, (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x52, (byte) 0x3e,
            (byte) 0x3e, (byte) 0x0a, (byte) 0x65, (byte) 0x6e, (byte) 0x64, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x39,
            (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x3c, (byte) 0x3c, (byte) 0x2f,
            (byte) 0x53, (byte) 0x75, (byte) 0x62, (byte) 0x74, (byte) 0x79, (byte) 0x70, (byte) 0x65, (byte) 0x2f, (byte) 0x54, (byte) 0x79,
            (byte) 0x70, (byte) 0x65, (byte) 0x31, (byte) 0x2f, (byte) 0x42, (byte) 0x61, (byte) 0x73, (byte) 0x65, (byte) 0x46, (byte) 0x6f,
            (byte) 0x6e, (byte) 0x74, (byte) 0x2f, (byte) 0x54, (byte) 0x69, (byte) 0x6d, (byte) 0x65, (byte) 0x73, (byte) 0x2d, (byte) 0x52,
            (byte) 0x6f, (byte) 0x6d, (byte) 0x61, (byte) 0x6e, (byte) 0x2f, (byte) 0x54, (byte) 0x79, (byte) 0x70, (byte) 0x65, (byte) 0x2f,
            (byte) 0x46, (byte) 0x6f, (byte) 0x6e, (byte) 0x74, (byte) 0x2f, (byte) 0x4e, (byte) 0x61, (byte) 0x6d, (byte) 0x65, (byte) 0x2f,
            (byte) 0x52, (byte) 0x39, (byte) 0x3e, (byte) 0x3e, (byte) 0x0a, (byte) 0x65, (byte) 0x6e, (byte) 0x64, (byte) 0x6f, (byte) 0x62,
            (byte) 0x6a, (byte) 0x0a, (byte) 0x38, (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x0a,
            (byte) 0x3c, (byte) 0x3c, (byte) 0x2f, (byte) 0x54, (byte) 0x79, (byte) 0x70, (byte) 0x65, (byte) 0x2f, (byte) 0x46, (byte) 0x6f,
            (byte) 0x6e, (byte) 0x74, (byte) 0x44, (byte) 0x65, (byte) 0x73, (byte) 0x63, (byte) 0x72, (byte) 0x69, (byte) 0x70, (byte) 0x74,
            (byte) 0x6f, (byte) 0x72, (byte) 0x2f, (byte) 0x46, (byte) 0x6f, (byte) 0x6e, (byte) 0x74, (byte) 0x4e, (byte) 0x61, (byte) 0x6d,
            (byte) 0x65, (byte) 0x2f, (byte) 0x54, (byte) 0x69, (byte) 0x6d, (byte) 0x65, (byte) 0x73, (byte) 0x2d, (byte) 0x52, (byte) 0x6f,
            (byte) 0x6d, (byte) 0x61, (byte) 0x6e, (byte) 0x3e, (byte) 0x3e, (byte) 0x0a, (byte) 0x65, (byte) 0x6e, (byte) 0x64, (byte) 0x6f,
            (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x32, (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x6f, (byte) 0x62, (byte) 0x6a,
            (byte) 0x0a, (byte) 0x3c, (byte) 0x3c, (byte) 0x2f, (byte) 0x50, (byte) 0x72, (byte) 0x6f, (byte) 0x64, (byte) 0x75, (byte) 0x63,
            (byte) 0x65, (byte) 0x72, (byte) 0x28, (byte) 0x47, (byte) 0x4e, (byte) 0x55, (byte) 0x20, (byte) 0x47, (byte) 0x68, (byte) 0x6f,
            (byte) 0x73, (byte) 0x74, (byte) 0x73, (byte) 0x63, (byte) 0x72, (byte) 0x69, (byte) 0x70, (byte) 0x74, (byte) 0x20, (byte) 0x37,
            (byte) 0x2e, (byte) 0x30, (byte) 0x35, (byte) 0x29, (byte) 0x3e, (byte) 0x3e, (byte) 0x65, (byte) 0x6e, (byte) 0x64, (byte) 0x6f,
            (byte) 0x62, (byte) 0x6a, (byte) 0x0a, (byte) 0x78, (byte) 0x72, (byte) 0x65, (byte) 0x66, (byte) 0x0a, (byte) 0x30, (byte) 0x20,
            (byte) 0x31, (byte) 0x32, (byte) 0x0a, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30,
            (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x20, (byte) 0x36, (byte) 0x35, (byte) 0x35, (byte) 0x33, (byte) 0x35, (byte) 0x20,
            (byte) 0x66, (byte) 0x20, (byte) 0x0a, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30,
            (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x20, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x20,
            (byte) 0x6e, (byte) 0x20, (byte) 0x0a, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30,
            (byte) 0x37, (byte) 0x32, (byte) 0x39, (byte) 0x20, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x20,
            (byte) 0x6e, (byte) 0x20, (byte) 0x0a, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30,
            (byte) 0x33, (byte) 0x37, (byte) 0x33, (byte) 0x20, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x20,
            (byte) 0x6e, (byte) 0x20, (byte) 0x0a, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30,
            (byte) 0x34, (byte) 0x38, (byte) 0x30, (byte) 0x20, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x20,
            (byte) 0x6e, (byte) 0x20, (byte) 0x0a, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30,
            (byte) 0x32, (byte) 0x31, (byte) 0x33, (byte) 0x20, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x20,
            (byte) 0x6e, (byte) 0x20, (byte) 0x0a, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30,
            (byte) 0x30, (byte) 0x31, (byte) 0x35, (byte) 0x20, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x20,
            (byte) 0x6e, (byte) 0x20, (byte) 0x0a, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30,
            (byte) 0x31, (byte) 0x39, (byte) 0x34, (byte) 0x20, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x20,
            (byte) 0x6e, (byte) 0x20, (byte) 0x0a, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30,
            (byte) 0x36, (byte) 0x36, (byte) 0x38, (byte) 0x20, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x20,
            (byte) 0x6e, (byte) 0x20, (byte) 0x0a, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30,
            (byte) 0x35, (byte) 0x39, (byte) 0x35, (byte) 0x20, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x20,
            (byte) 0x6e, (byte) 0x20, (byte) 0x0a, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30,
            (byte) 0x35, (byte) 0x33, (byte) 0x35, (byte) 0x20, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x20,
            (byte) 0x6e, (byte) 0x20, (byte) 0x0a, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30,
            (byte) 0x35, (byte) 0x36, (byte) 0x35, (byte) 0x20, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x20,
            (byte) 0x6e, (byte) 0x20, (byte) 0x0a, (byte) 0x74, (byte) 0x72, (byte) 0x61, (byte) 0x69, (byte) 0x6c, (byte) 0x65, (byte) 0x72,
            (byte) 0x0a, (byte) 0x3c, (byte) 0x3c, (byte) 0x20, (byte) 0x2f, (byte) 0x53, (byte) 0x69, (byte) 0x7a, (byte) 0x65, (byte) 0x20,
            (byte) 0x31, (byte) 0x32, (byte) 0x20, (byte) 0x2f, (byte) 0x52, (byte) 0x6f, (byte) 0x6f, (byte) 0x74, (byte) 0x20, (byte) 0x31,
            (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x52, (byte) 0x20, (byte) 0x2f, (byte) 0x49, (byte) 0x6e, (byte) 0x66, (byte) 0x6f,
            (byte) 0x20, (byte) 0x32, (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x52, (byte) 0x0a, (byte) 0x3e, (byte) 0x3e, (byte) 0x0a,
            (byte) 0x73, (byte) 0x74, (byte) 0x61, (byte) 0x72, (byte) 0x74, (byte) 0x78, (byte) 0x72, (byte) 0x65, (byte) 0x66, (byte) 0x0a,
            (byte) 0x37, (byte) 0x37, (byte) 0x39, (byte) 0x0a, (byte) 0x25, (byte) 0x25, (byte) 0x45, (byte) 0x4f, (byte) 0x46, (byte) 0x0a};

    private ExampleData() { // to avoid initializations
    }
}