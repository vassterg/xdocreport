/**
 * Copyright (C) 2011 Angelo Zerr <angelo.zerr@gmail.com> and Pascal Leclercq <pascal.leclercq@gmail.com>
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.opensagres.xdocreport.document.docx;

import static fr.opensagres.xdocreport.document.docx.DocXConstants.A_NS;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.BLIP_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.BOOKMARK_END_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.BOOKMARK_START_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.CONTENT_TYPES_XML;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.EXTENT_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.EXT_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.FLDCHAR_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.FLDSIMPLE_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.HYPERLINK_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.INSTR_TEXT_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.P_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.RFONTS_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.R_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.TBL_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.TR_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.T_ELT;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.WP_NS;
import static fr.opensagres.xdocreport.document.docx.DocXConstants.W_NS;
import fr.opensagres.xdocreport.core.io.XDocArchive;

/**
 * Utilities for docx.
 */
public class DocxUtils
{

    /**
     * Returns true if the given document archive is a docx and false otherwise.
     * 
     * @param documentArchive
     * @return
     */

    public static boolean isDocx( XDocArchive documentArchive )
    {
        if ( !documentArchive.hasEntry( CONTENT_TYPES_XML ) )
        {
            return false;
        }
        // TODO check from [Content_Types].xml
        // <Override PartName="/word/document.xml"
        // ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"
        // />
        return documentArchive.getEntryNames( DocXConstants.WORD_REGEXP ).size() > 0;
    }

    public static boolean isTable( String uri, String localName, String name )
    {
        return W_NS.equals( uri ) && TBL_ELT.equals( localName );
    }

    public static boolean isTableRow( String uri, String localName, String name )
    {
        return W_NS.equals( uri ) && TR_ELT.equals( localName );
    }

    /**
     * Returns true if current element is w:fldSimple and false otherwise.
     * 
     * @param uri
     * @param localName
     * @param name
     * @return
     */
    public static boolean isFldSimple( String uri, String localName, String name )
    {
        return ( W_NS.equals( uri ) && FLDSIMPLE_ELT.equals( localName ) );
    }

    /**
     * Returns true if current element is w:fldSimple with MERGEFIELD type and false otherwise. If element is
     * w:fldSimple :
     * 
     * <pre>
     * <w:fldSimple w:instr=" MERGEFIELD  ${name} ">
     * 		<w:r w:rsidR="00396432">
     * 			<w:rPr>
     * 				<w:noProof/>
     * 				</w:rPr>
     * 				<w:t>�${name}�</w:t>
     * 		</w:r>
     * 	</w:fldSimple>
     * </pre>
     * 
     * it is transformed with
     * 
     * <pre>
     * <w:r w:rsidR="00396432">
     * 			<w:rPr>
     * 				<w:noProof/>
     * 				</w:rPr>
     * 				<w:t>${name}</w:t>
     * 		</w:r>
     * </pre>
     * 
     * @param uri
     * @param localName
     * @param name
     * @return
     */

    public static boolean isP( String uri, String localName, String name )
    {
        return ( W_NS.equals( uri ) && P_ELT.equals( localName ) );
    }

    public static boolean isR( String uri, String localName, String name )
    {
        return ( W_NS.equals( uri ) && R_ELT.equals( localName ) );
    }

    public static boolean isT( String uri, String localName, String name )
    {
        return ( W_NS.equals( uri ) && T_ELT.equals( localName ) );
    }

    public static boolean isFldChar( String uri, String localName, String name )
    {
        return ( W_NS.equals( uri ) && FLDCHAR_ELT.equals( localName ) );
    }

    public static boolean isInstrText( String uri, String localName, String name )
    {
        return ( W_NS.equals( uri ) && INSTR_TEXT_ELT.equals( localName ) );
    }

    /**
     * Returns true if current element is w:bookmarkStart and false otherwise.
     * 
     * @param uri
     * @param localName
     * @param name
     * @return
     */
    public static boolean isBookmarkStart( String uri, String localName, String name )
    {
        return ( W_NS.equals( uri ) && BOOKMARK_START_ELT.equals( localName ) );
    }

    /**
     * Returns true if current element is w:bookmarkEnd and false otherwise.
     * 
     * @param uri
     * @param localName
     * @param name
     * @return
     */
    public static boolean isBookmarkEnd( String uri, String localName, String name )
    {
        return ( W_NS.equals( uri ) && BOOKMARK_END_ELT.equals( localName ) );
    }

    /**
     * Returns true if current element is a:blip and false otherwise.
     * 
     * @param uri
     * @param localName
     * @param name
     * @return
     */
    public static boolean isBlip( String uri, String localName, String name )
    {
        return ( A_NS.equals( uri ) && BLIP_ELT.equals( localName ) );
    }

    /**
     * Returns true if current element is wp:extent and false otherwise.
     * 
     * @param uri
     * @param localName
     * @param name
     * @return
     */
    public static boolean isExtent( String uri, String localName, String name )
    {
        return ( WP_NS.equals( uri ) && EXTENT_ELT.equals( localName ) );
    }

    /**
     * Returns true if current element is a:ext and false otherwise.
     * 
     * @param uri
     * @param localName
     * @param name
     * @return
     */
    public static boolean isExt( String uri, String localName, String name )
    {
        return ( A_NS.equals( uri ) && EXT_ELT.equals( localName ) );
    }

    /**
     * Returns true if current element is w:hyperlink and false otherwise.
     * 
     * @param uri
     * @param localName
     * @param name
     * @return
     */
    public static boolean isHyperlink( String uri, String localName, String name )
    {
        return ( W_NS.equals( uri ) && HYPERLINK_ELT.equals( localName ) );
    }

    public static boolean isRFonts( String uri, String localName, String name )
    {
        return W_NS.equals( uri ) && RFONTS_ELT.equals( localName );
    }
}
