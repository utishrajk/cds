package com.feisystems.bham.infrastructure;


import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.feisystems.bham.service.AsiFlagDto;
import com.feisystems.bham.service.DataElementsDto;
import com.feisystems.bham.service.GpraFlagDto;
import com.feisystems.bham.service.RecommendationsDto;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.List;


/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * 
 * @author www.codejava.net
 *
 */
public class PDFBuilder extends AbstractITextPdfView {
	private final int TABLECELL_FONT_SIZE = 12;
	private final int LOGO_FONT_SIZE = 18;
	private final int RECOMENDATONS_TITLE_SIZE = 21;
	private final int RECOMENDATONS_LIST_TITLE = 14;
	private String note1;
	private String uniqueClientNumber;
	private String timestamp;
	private String username;
	private String bhcdsColor = "#7895a3";
	private String bhcdsLogoStr = "Behavioral Health: Clinical Decision Support";
	
	
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,PdfWriter writer, HttpServletRequest request, HttpServletResponse response){
    	
    	try {
    		 // get data model which is passed by the Spring container
            RecommendationsDto recommendationsDto = (RecommendationsDto) model.get("recommendationsDto");   
            
            username = (String)model.get("fullName");
            
            AsiFlagDto asiDto = (AsiFlagDto) model.get("asiDto");
            GpraFlagDto gpraDto = null;
            TableHeader event = null;
            
            if(asiDto != null){
            	note1 = asiDto.getNote1();
            	uniqueClientNumber= asiDto.getUniqueClientNumber();
            	timestamp = asiDto.getTimeStamp().toString();
            	event = new TableHeader(note1, uniqueClientNumber, timestamp, writer.getDirectContent().createTemplate(30, 16) );
            }else{
            	gpraDto = (GpraFlagDto) model.get("gpraDto");
            	if(gpraDto != null){
            		note1 = gpraDto.getNote1();
            		uniqueClientNumber= gpraDto.getUniqueClientNumber();
                	timestamp = gpraDto.getTimeStamp().toString();
            		event = new TableHeader(note1, uniqueClientNumber, timestamp, writer.getDirectContent().createTemplate(30, 16));
            	}
            }
            
            //Adding the event listener for the header and footer 
            writer.setPageEvent(event);
            
            Paragraph titleParagraph = new Paragraph();
            titleParagraph.setFont(createFont(RECOMENDATONS_TITLE_SIZE,true, false));
            
            titleParagraph.add(new Paragraph("BHCDS Recommendations"));
            titleParagraph.add( Chunk.NEWLINE ); 
            
            Paragraph p1 = new Paragraph("For ");
            Font f2 = createFont(RECOMENDATONS_TITLE_SIZE,true, false);
            f2.setStyle(Font.ITALIC);
            p1.add(new Phrase(note1 + " ",f2));
            p1.add(new Phrase(" on"));
            titleParagraph.add(p1);
            
            titleParagraph.add( Chunk.NEWLINE ); 
            
            Font f3 = createFont(RECOMENDATONS_TITLE_SIZE,true, false);
            f3.setStyle(Font.ITALIC);
            
            titleParagraph.add(new Paragraph(timestamp, f3));
            doc.add(titleParagraph);
            doc.add( Chunk.NEWLINE );           
            
            //Description         
            doc.add(new Paragraph(recommendationsDto.getDescription()));
            
            doc.add( Chunk.NEWLINE );
            
            doc.add(new Paragraph("Recommendations for this kind of patient group:", createFont(RECOMENDATONS_LIST_TITLE,true, false)));         
            doc.add( Chunk.NEWLINE );
            
            doc.add(new Paragraph("Pay special attention to potential:"));         
            doc.add( Chunk.NEWLINE );
            
            //Creating Recommendations list                  
            doc.add(createUnorderList( recommendationsDto.getRecommendations() ));
            
            doc.add( Chunk.NEWLINE );
            
            doc.add(new Paragraph("Services / Treatment for this kind of patient group:", createFont(RECOMENDATONS_LIST_TITLE, true, false)));         
            doc.add( Chunk.NEWLINE );
            
            doc.add(new Paragraph("You should consider:"));         
            doc.add( Chunk.NEWLINE );
            
            //Creating Recommendations list                  
            doc.add(createUnorderList( recommendationsDto.getServices() ));
            
            doc.add( Chunk.NEWLINE );
            
            doc.add(new Paragraph("Individuals in this group profile tended to do best with the following services:", createFont(RECOMENDATONS_LIST_TITLE, true, false)));         
            doc.add( Chunk.NEWLINE );
                        
            //Creating Recommendations list                  
            doc.add(createUnorderList( recommendationsDto.getTreatments() ));
            
            doc.add( Chunk.NEWLINE );
            
            //creating the data elements tables
            if(asiDto!=null){
           	 	doc.add(createAsiTable(asiDto));
            }else if(gpraDto!=null){
           	 	doc.add(createGpraTable(gpraDto));
            }
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
    }
    
    private List createUnorderList(java.util.List<String> recommendationlist){
    	com.itextpdf.text.List unorderedList = new List(List.UNORDERED);
       
    	for(String item: recommendationlist){
    		 unorderedList.add(new ListItem(item));
        }
    	 
    	return unorderedList;
    }
    
    private Font createFont(int size, boolean isBold, boolean isRed){
    	Font font = null;
    	
    	if (isBold){
    		font = new Font(Font.FontFamily.HELVETICA, size, Font.BOLD);
    	}else{
    		font = new Font(Font.FontFamily.HELVETICA, size);
    	}
    	
    	if(isRed){    		
    		font.setColor(BaseColor.RED);
    	}else{
    		font.setColor(BaseColor.BLACK);
    	}
    	
        return font;
    }
    
    private PdfPCell createTableCell(String value,int size,  boolean isBold, boolean flag){
    	 PdfPCell cell = new PdfPCell();
         cell.setPadding(5);
         
         Paragraph p = new Paragraph();
         
         if(flag){
        	 p.add(new Chunk("*", createFont(size, isBold, flag)));
         }
         
         p.add(new Chunk(value, createFont(size, isBold, false)));
         
         cell.addElement(p);
         
        return cell;
    }
    
    private PdfPCell createTableCellWithColor(String content, int size){
    	PdfPCell cell = new PdfPCell();
    	
    	Paragraph p = new Paragraph();
        p.add(new Chunk(content, new Font(Font.FontFamily.HELVETICA, size, Font.BOLD, WebColors.getRGBColor("#fff"))));
         
        cell.addElement(p);
        cell.setPadding(5);
        
        return cell;
    }
    
    
    private PdfPCell createPDETitleCell(){
    	PdfPCell cell = new PdfPCell();
    	
    	cell.setBorderWidthRight(0);
    	cell.setBorderWidthLeft(0);
    	cell.setBorderWidthTop(0);
    	cell.setBorderWidthBottom(0);
    	cell.setColspan(2);
    	
    	Paragraph title  = new Paragraph("Data elements used for the above recommendations:", createFont(RECOMENDATONS_LIST_TITLE, true, false));
    	title.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(title);
        cell.setPadding(5);
        
        return cell;
    }
    
    
    private PdfPCell createCellWithEditedInfo(){
    	PdfPCell cell = new PdfPCell();
    	
    	cell.setBorderWidthRight(0);
    	cell.setBorderWidthLeft(0);
    	cell.setBorderWidthTop(0);
    	cell.setBorderWidthBottom(0);
    	cell.setColspan(2);
 		
    	Paragraph editedBy = new Paragraph();
        editedBy.add(new Chunk("*", createFont(TABLECELL_FONT_SIZE, false, true)));
        editedBy.add(new Chunk("Edited by " + username + "  " + timestamp, createFont(TABLECELL_FONT_SIZE, false, false)));
        editedBy.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(editedBy);
        cell.setPadding(5);
        
        return cell;
    }
    
    private PdfPTable createEmptyTable(){
    	PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);        
        
        try {
				table.setWidths(new float[] { 9.0f, 3.0f});
				table.setSpacingBefore(10);
								
				PdfPCell  cell0 = createPDETitleCell();
				table.addCell(cell0);
				 
				PdfPCell  cell01 = createCellWithEditedInfo();
		        table.addCell(cell01 );
		        
		        
		        BaseColor bgColor = WebColors.getRGBColor(bhcdsColor);
		        
		        PdfPCell  cell11 = createTableCellWithColor("Fields",TABLECELL_FONT_SIZE);
				cell11.setBackgroundColor(bgColor);
		        table.addCell(cell11 );
		        
		        PdfPCell  cell12 = createTableCellWithColor("Answer", TABLECELL_FONT_SIZE);
		        cell12.setBackgroundColor(bgColor);
		        table.addCell(cell12);
		        
		        table.setHeaderRows(3);
		        
		 } catch (DocumentException e) {
				e.printStackTrace();
	     }
        
        return table;
    }
    
    private String composeDayCount(int value){    	
    	
    	String stringValue = null;
    	
    	if(value == -7){
    		stringValue = "Refuse";
    	}else if(value == -8){
    		stringValue = "Don't know";
    	}else {
    		stringValue = value + "";
    	}
    	return stringValue;    	
    }
    
    
    
    private PdfPTable createGpraTable( GpraFlagDto gpraDto){
    	
    	PdfPTable table = createEmptyTable();
    	
    	table.addCell( createTableCell("What is your age?",TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getAge().toString(), TABLECELL_FONT_SIZE, false, gpraDto.isAgeFlag()));
        
        table.addCell( createTableCell("What is your gender?",TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getGenderDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isGenderCodeFlag()));
        
        
        PdfPCell cell= createTableCell("During the past 30 days, how many days have you used any of the following:",12, true, false);
        cell.setColspan(2);
        table.addCell(cell );
        
        table.addCell( createTableCell("Heroin (smack, H, Junk, Skag) - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getHeroinDayCount()), TABLECELL_FONT_SIZE, false, gpraDto.isHeroinDayCountFlag()));
        
        table.addCell( createTableCell("Heroin (smack, H, Junk, Skag) - route", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getHeroinRouteDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isHeroinRouteCodeFlag()));
        
        table.addCell( createTableCell("Morphine - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getMorphineCount()), TABLECELL_FONT_SIZE, false, gpraDto.isMorphineCountFlag()));
        
        table.addCell( createTableCell("Morphine - route", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getMorphineRouteDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isMorphineRouteCodeFlag()));
        
        table.addCell( createTableCell("Diluadid - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getDiluadidCount()), TABLECELL_FONT_SIZE, false, gpraDto.isDiluadidCountFlag()));
        
        table.addCell( createTableCell("Diluadid - route", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getDiluadidRouteDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isDiluadidRouteCodeFlag()));
        
        table.addCell( createTableCell("Demerol - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getDemerolCount()), TABLECELL_FONT_SIZE, false, gpraDto.isDemerolCountFlag()));
        
        table.addCell( createTableCell("Demerol - route", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getDemerolRouteDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isDemerolRouteCodeFlag()));
        
        table.addCell( createTableCell("Percocet - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getPercocetCount()), TABLECELL_FONT_SIZE, false, gpraDto.isPercocetCountFlag()));
        
        table.addCell( createTableCell("Percocet - route", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getPercocetRouteDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isPercocetRouteCodeFlag()));
        
        table.addCell( createTableCell("Darvon - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getDarvonCount()), TABLECELL_FONT_SIZE, false, gpraDto.isDarvonCountFlag()));
        
        table.addCell( createTableCell("Darvon - route", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getDarvonRouteDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isDarvonRouteCodeFlag()));
        
        table.addCell( createTableCell("Codeine - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getCodeineCount()), TABLECELL_FONT_SIZE, false, gpraDto.isCodeineCountFlag()));
        
        table.addCell( createTableCell("Codeine - route", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getCodeineRouteDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isCodeineRouteCodeFlag()));
        
        table.addCell( createTableCell("Tylenol 2, 3, 4 - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getTylenolCount()), TABLECELL_FONT_SIZE, false, gpraDto.isTylenolCountFlag()));
        
        table.addCell( createTableCell("Tylenol 2, 3, 4 - route", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getTylenolRouteDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isTylenolRouteCodeFlag()));
        
        table.addCell( createTableCell("Oxycontin/Oxycodone - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getOxycontinCount()), TABLECELL_FONT_SIZE, false, gpraDto.isOxycontinCountFlag()));
        
        
        table.addCell( createTableCell("Oxycontin/Oxycodone - route", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getOxycontinRouteDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isOxycontinRouteCodeFlag()));
        
        table.addCell( createTableCell("Non-prescription methadone - number of day", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getMethadoneCount()), TABLECELL_FONT_SIZE, false, gpraDto.isMethadoneCountFlag()));
        
        table.addCell( createTableCell("Non-prescription methadone - route", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getMethadoneRouteDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isMethadoneRouteCodeFlag()));
        
        
        table.addCell( createTableCell("How would you rate your overall health right now?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getHealthIndicatorCodeDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isHealthIndicatorCodeFlag()));
        

        PdfPCell cell1= createTableCell("Have you had a significant period of time in which you have:", TABLECELL_FONT_SIZE, true, false);
        cell1.setColspan(2);
        table.addCell(cell1 );
        
        table.addCell( createTableCell("Experienced serious depression? - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getDepressionCount()), TABLECELL_FONT_SIZE, false, gpraDto.isDepressionCountFlag()));
        
        table.addCell( createTableCell("Experienced serious anxiety or tension? - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getAnxietyCount()), TABLECELL_FONT_SIZE, false, gpraDto.isAnxietyCountFlag()));
        
        table.addCell( createTableCell("Experienced trouble controlling violent behavior including episodes of rage, or violence? - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getViolentCount()), TABLECELL_FONT_SIZE, false, gpraDto.isViolentCountFlag()));
        
        table.addCell( createTableCell("Been prescribed medication for psychological and emotional problems? - number of days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(composeDayCount(gpraDto.getPsychMedicationCount()), TABLECELL_FONT_SIZE, false, gpraDto.isPsychMedicationCountFlag()));
        
        table.addCell( createTableCell("How much have you been bothered by these psychological or emotional problems in the past 30 days?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(gpraDto.getEmotionalProblemsDisplayName(), TABLECELL_FONT_SIZE, false, gpraDto.isEmotionalProblemsCodeFlag()));
        
        
    	return table;
    }
    
    private PdfPTable createAsiTable(AsiFlagDto asiDto){
    	
		PdfPTable table = createEmptyTable();
    	
		table.addCell( createTableCell("What is your age?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getAge().toString(), TABLECELL_FONT_SIZE, false, asiDto.isAgeFlag()));
        
        table.addCell( createTableCell("What is your gender?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getGenderDisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isGenderCodeFlag()));
        
        table.addCell( createTableCell("How many days have you experienced medical problems in the last 30?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getM6_days_medical_problem().toString(), TABLECELL_FONT_SIZE, false, asiDto.isM6_days_medical_problemFlag()));
        
        table.addCell( createTableCell("How troubled or bothered have you been by these medical problems in the past 30 days?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getM7_patient_rating_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isM7_patient_rating_CodeFlag()));
        
        table.addCell( createTableCell("How important to you now is treatment for these medical problems?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getM8_patient_rating_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isM8_patient_rating_CodeFlag()));
        
        table.addCell( createTableCell("How much money did you receive from illegal sources in the past 30 days?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getE17_income_illegal().toString(), TABLECELL_FONT_SIZE, false, asiDto.isE17_income_illegalFlag()));
        
        table.addCell( createTableCell("How many days have you experienced employment problems in past 30 days?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getE19_days_employment_problem().toString(), TABLECELL_FONT_SIZE, false, asiDto.isE19_days_employment_problemFlag()));
        
        table.addCell( createTableCell("Alcohol - to intoxication (3 drinks in a setting or 5 in a day) - days in the last 30", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getD2a_intoxication_30().toString(), TABLECELL_FONT_SIZE, false, asiDto.isD2a_intoxication_30Flag()));
        
        table.addCell( createTableCell("Alcohol - route of administration", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell("", TABLECELL_FONT_SIZE, false, false));
        
        table.addCell( createTableCell("Heroin - days in the last 30", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getD3a_heroin_30().toString(), TABLECELL_FONT_SIZE, false, asiDto.isD3a_heroin_30Flag()));
        
        table.addCell( createTableCell("Heroin - route of administration", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getD3c_route_of_intake_type_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isD3c_route_of_intake_type_CodeFlag()));
        
        table.addCell( createTableCell("Methadone - days in the last 30", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getD4a_methadone_30().toString(), TABLECELL_FONT_SIZE, false, asiDto.isD4a_methadone_30Flag()));
        
        table.addCell( createTableCell("Methadone - route of administration", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getD4c_route_of_intake_type_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isD4c_route_of_intake_type_CodeFlag()));
        
        table.addCell( createTableCell("Other opiates/analgesics - days in the last 30", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getD5a_opiates_30().toString(), TABLECELL_FONT_SIZE, false, asiDto.isD5a_opiates_30Flag()));
        
        table.addCell( createTableCell("Other opiates/analgesics - route of administration30", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getD5c_route_of_intake_type_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isD5c_route_of_intake_type_CodeFlag()));
        
        table.addCell( createTableCell("Are you presently awaiting charges, trial, or sentence?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getL24_is_waiting_trial_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isL24_is_waiting_trial_CodeFlag()));
        
        table.addCell( createTableCell("How many days in the past 30 have you engaged in illegal activity for profit?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getL27_days_illegal().toString(), TABLECELL_FONT_SIZE, false, asiDto.isL27_days_illegalFlag()));
        
        table.addCell( createTableCell("How serious do you feel your present legal problems are?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getL28_patient_rating_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isL28_patient_rating_CodeFlag()));
        
        table.addCell( createTableCell("How important to you now is counseling or referral for these legal problems?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getL29_patient_rating_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isL29_patient_rating_CodeFlag()));
        
        table.addCell( createTableCell("Are you satisfied with your current marital status?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF3_is_satisfied_marital_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF3_is_satisfied_marital_CodeFlag()));
        
        
        PdfPCell cell1= createTableCell("Have you had significant periods in which you have experienced serious problems with:", TABLECELL_FONT_SIZE, true, false);
        cell1.setColspan(2);
        table.addCell(cell1 );
        
        
        table.addCell( createTableCell("Mother - in the past 30 days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF18a_is_mother_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF18a_is_mother_30_CodeFlag()));
        
        table.addCell( createTableCell("Father - in the past 30 days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF19a_is_father_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF19a_is_father_30_CodeFlag()));
        
        table.addCell( createTableCell("Brother/Sisters  - in the past 30 days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF20a_is_sibling_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF20a_is_sibling_30_CodeFlag()));
        
        table.addCell( createTableCell("Sexual Partners/Spouse - in the past 30 days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF21a_is_spouse_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF21a_is_spouse_30_CodeFlag()));
        
        table.addCell( createTableCell("Children - in the past 30 ", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF22a_is_children_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF22a_is_children_30_CodeFlag()));
        
        table.addCell( createTableCell("Other significant family - in the past 30 days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF23a_is_other_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF23a_is_other_30_CodeFlag()));
        
        table.addCell( createTableCell("Close friends  - in the past 30 days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF24a_is_friends_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF24a_is_friends_30_CodeFlag()));
        
        table.addCell( createTableCell("Neighbors  - in the past 30 days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF25a_is_neighbor_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF25a_is_neighbor_30_CodeFlag()));
        
        table.addCell( createTableCell("Coworkers  - in the past 30 days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF26a_is_co_worker_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF26a_is_co_worker_30_CodeFlag()));
        
        
        table.addCell( createTableCell("Has anyone ever abused you physical?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF28b_is_abused_p_lifetime_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF28b_is_abused_p_lifetime_CodeFlag()));
        
        table.addCell( createTableCell("Has anyone ever abused you sexually?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF29b_is_abused_s_lifetime_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF29b_is_abused_s_lifetime_CodeFlag()));
        
        table.addCell( createTableCell("How many days in the past 30 have you had serious conflicts with your family?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF30_days_conflict_family().toString(), TABLECELL_FONT_SIZE, false, asiDto.isF30_days_conflict_familyFlag()));
        
        table.addCell( createTableCell("How troubled or bothered have you been in the past 30 days by family problems?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF32_patient_rating_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF32_patient_rating_CodeFlag()));
        
        table.addCell( createTableCell("How important to you now is treatment or counseling for family problems?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getF34_patient_rating_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isF34_patient_rating_CodeFlag()));
        
        
        PdfPCell cell2= createTableCell("Have you had a significant period of time in which you have", TABLECELL_FONT_SIZE, true, false);
        cell2.setColspan(2);
        table.addCell(cell2 );
        
        table.addCell( createTableCell("Experienced serious depression? in the past 30 days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getP4a_is_depression_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isP4a_is_depression_30_CodeFlag()));
        
        table.addCell( createTableCell("Experienced serious anxiety or tension? in the past 30 days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getP5a_is_anxiety_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isP5a_is_anxiety_30_CodeFlag()));
        
        table.addCell( createTableCell("Experienced trouble controlling violent behavior including episodes of rage, or violence? in the past 30 days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getP8a_is_violent_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isP8a_is_violent_30_CodeFlag()));
        
        table.addCell( createTableCell("Experienced serious thoughts of suicide? in the past 30 days:", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getP9a_is_suicidal_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isP9a_is_suicidal_30_CodeFlag()));
        
        table.addCell( createTableCell("Been prescribed medication for psychological and emotional problems? in the past 30 days", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getP11a_is_prescribed_30_DisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isP11a_is_prescribed_30_CodeFlag()));
        
        table.addCell( createTableCell("How many prior treatment episodes have you had for alcohol and/or drugs?", TABLECELL_FONT_SIZE, true, false));
        table.addCell(createTableCell(asiDto.getEpisodeDisplayName(), TABLECELL_FONT_SIZE, false, asiDto.isEpisodeCodeFlag()));
	        
    	return table;
    }
    
	class TableHeader extends PdfPageEventHelper {

		private PdfTemplate total ;
		private String clientReferenceNote;
		private String uniqueClientNumber;
		private String dateTime;
		
		private Font font = createFont(10, false, false);
		
		DataElementsDto dto;
		
		public TableHeader(String note1, String uniqueClientNumber, String date,  PdfTemplate template) {
			 this.total = template;
			 this.clientReferenceNote = note1;
			 this.uniqueClientNumber = uniqueClientNumber;
			 this.dateTime = date;
		}

		public void onOpenDocument(PdfWriter writer, Document document) {
		}

		public void onStartPage(PdfWriter writer, Document document) {
		}
	     
		public void onEndPage(PdfWriter writer, Document document) {
			addFooter(writer, document);
			addHeader(writer, document);
		}

		public void onCloseDocument(PdfWriter writer, Document document) {
			ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);
		}
		

		private void addFooter(PdfWriter writer, Document document){

			
			PdfPTable table = new PdfPTable(1);
	    	 
			try {

				table.setWidths(new int[] { 50});
				table.setTotalWidth(527);
				table.setLockedWidth(true);
				
				PdfPCell cell = new PdfPCell();
				cell.setFixedHeight(20);
				cell.setBorder(Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				
				Paragraph p = new Paragraph();
				p.add(new Chunk("© "+ currentYear + " Inflexxion Inc. All right reserved.", font));		
				p.setAlignment(Element.ALIGN_RIGHT);
				cell.addElement(p);
				
				table.addCell(cell);				
				table.writeSelectedRows(0, -1,34, 30, writer.getDirectContent());
			}

			catch (DocumentException de) {
				throw new ExceptionConverter(de);
			}
		}
	
		private void addHeader(PdfWriter writer, Document document){
			PdfPTable table = new PdfPTable(2);

			try {

				table.setWidths(new int[] { 25, 25 });
				table.setTotalWidth(527);
				table.setLockedWidth(true);
				table.getDefaultCell().setFixedHeight(45);
				table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				table.getDefaultCell().setPaddingLeft(15);
				
				BaseColor bgColor = WebColors.getRGBColor(bhcdsColor);
				
				Font logoFont = new Font(Font.FontFamily.HELVETICA, LOGO_FONT_SIZE, Font.BOLD);
				logoFont.setColor(BaseColor.WHITE);
				
				Paragraph logoPara = new Paragraph();
				Chunk ck = new Chunk(bhcdsLogoStr, logoFont);
				ck.setBackground(bgColor);
				logoPara.add(ck);
				
				PdfPCell leftHeaderCell = new PdfPCell();
				leftHeaderCell.setBackgroundColor(bgColor);
				leftHeaderCell.setPaddingTop(7);
				leftHeaderCell.setPaddingLeft(7);
				leftHeaderCell.setPaddingBottom(7);
				leftHeaderCell.setBorder(0);				
				leftHeaderCell.addElement(logoPara);	
		        
		        PdfPTable table2 = new PdfPTable(2);
		        table2.setTotalWidth(new float[]{ 10,250});
		        table2.setLockedWidth(true);
		        
		        PdfPCell emptyCell = new PdfPCell();
		        emptyCell.setBorder(0);
		        
		        table2.addCell(emptyCell);
		        table2.addCell(leftHeaderCell);		        
		        table.addCell(table2);
				
				Paragraph para = new Paragraph();
				para.add(new Chunk( this.clientReferenceNote + " " + this.uniqueClientNumber, font));
				para.add(Chunk.NEWLINE); 
				para.add(new Chunk(this.dateTime, font));
				para.add(Chunk.NEWLINE); 
				
				
				para.add(new Chunk(username, font));
				para.setAlignment(Element.ALIGN_RIGHT);
				
				PdfPCell rightHeaderCell = new PdfPCell();
				rightHeaderCell.setPaddingRight(15);
				rightHeaderCell.setBorder(0);
				rightHeaderCell.addElement(para);
				
				table.addCell(rightHeaderCell);
				table.writeSelectedRows(0, -1,34, 803, writer.getDirectContent());
				
			}catch (DocumentException de) {
				throw new ExceptionConverter(de);
			}
		}
	}
   
}