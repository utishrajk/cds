package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(Integer.class)
public enum EnumDays {
	
	@XmlEnumValue("0") ZERO(0),
	
    @XmlEnumValue("1") ONE(1),
    
    @XmlEnumValue("2") TWO(2),
    
    @XmlEnumValue("3") THREE(3),
    
    @XmlEnumValue("4") FOUR(4),
    
    @XmlEnumValue("5") FIVE(5),
    
    @XmlEnumValue("6") SIX(6),
    
    @XmlEnumValue("7") SEVEN(7),
    
    @XmlEnumValue("8") EIGHT(8),
    
    @XmlEnumValue("9") NINE(9),
    
    @XmlEnumValue("10") TEN(10),

    @XmlEnumValue("11") ELLEVEN(11),
    
    @XmlEnumValue("12") TWELVE(12),
    
    @XmlEnumValue("13") THIRTEEN(13),
    
    @XmlEnumValue("14") FOURTEEN(14),
    
    @XmlEnumValue("15") FIFTEEN(15),

    @XmlEnumValue("16") SIXTEEN(16),
    
    @XmlEnumValue("17") SEVENTEEN(17),
    
    @XmlEnumValue("18") EIGHTEEN(18),
    
    @XmlEnumValue("19") NINETEEN(19),
    
    @XmlEnumValue("20") TWENTY(20),

    @XmlEnumValue("21") TWENTYONE(21),
    
    @XmlEnumValue("22") TWENTYTWO(22),
    
    @XmlEnumValue("23") TWENTYTHREE(23),
    
    @XmlEnumValue("24") TWENTYFOUR(24),
    
    @XmlEnumValue("25") TWENTYFIVE(25),

    @XmlEnumValue("26") TWENTYSIX(26),
    
    @XmlEnumValue("27") TWENTYSEVEN(27),
    
    @XmlEnumValue("28") TWENTYEIGHT(28),
    
    @XmlEnumValue("29") TWENTYNINE(29),
    
    @XmlEnumValue("30") THIRTY(30),
    
    @XmlEnumValue("-7") REFUSED(-7),
    
    @XmlEnumValue("-8") DONT_KNOW(-8),
    
    @XmlEnumValue("-9") MISSING(-9);
	
	private int value;
    
    private EnumDays(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}