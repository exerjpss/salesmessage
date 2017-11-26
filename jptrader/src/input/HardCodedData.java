package input;

import java.util.ArrayList;

import core.constants.ConstSet;

public class HardCodedData implements IDataInput 

{
	
	int count = 0;
	ArrayList<String> lines = new ArrayList<>();
	
	public HardCodedData ()
	{
		lines.add(addSale ("Apple",10,20));
		lines.add(addSale ("Apple",15));
		lines.add(addSale ("Orange",7,7));
		lines.add(addSaleAdjustment ("Apple",10,2,ConstSet.ADJ_ADD,5));
		lines.add(addSale ("Melon",12,3));
		lines.add(addSaleAdjustment ("Apple",15,2,ConstSet.ADJ_ADD,5));
		lines.add(addSale ("Orange",14,1));
		lines.add(addSale ("Orange",3));
		lines.add(addSaleAdjustment ("Orange",17,2,ConstSet.ADJ_SUB,1));
		lines.add(addSaleAdjustment ("Banana",13,5,ConstSet.ADJ_MUL,3));
		
		lines.add(addSale ("Apple",10,20));
		lines.add(addSale ("Apple",15));
		lines.add(addSale ("Orange",7,7));
		lines.add(addSaleAdjustment ("Apple",12,5,ConstSet.ADJ_ADD,5));
		lines.add(addSale ("Melon",12,3));
		lines.add(addSaleAdjustment ("Melon",15,7,ConstSet.ADJ_ADD,5));
		lines.add(addSale ("Satsuma",23,1));
		lines.add(addSale ("Grapes",13));
		lines.add(addSaleAdjustment ("Orange",17,1,ConstSet.ADJ_SUB,1));
		lines.add(addSaleAdjustment ("Banana",13,1,ConstSet.ADJ_MUL,2));

		lines.add(addSale ("Apple",10,20));
		lines.add(addSale ("Apple",15));
		lines.add(addSale ("Orange",7,7));
		lines.add(addSaleAdjustment ("Grapes",10,5,ConstSet.ADJ_ADD,2));
		lines.add(addSale ("Melon",12,3));
		lines.add(addSaleAdjustment ("Coconut",16,8,ConstSet.ADJ_ADD,9));
		lines.add(addSale ("Grapes",14,1));
		lines.add(addSale ("Grapes",8));
		lines.add(addSaleAdjustment ("Orange",17,2,ConstSet.ADJ_ADD,8));
		lines.add(addSaleAdjustment ("Grapes",13,5,ConstSet.ADJ_MUL,3));

		lines.add(addSale ("Apple",10,20));
		lines.add(addSale ("Apple",15));
		lines.add(addSale ("Orange",7,7));
		lines.add(addSaleAdjustment ("Apple",10,20,ConstSet.ADJ_ADD,5));
		lines.add(addSale ("Melon",12,3));
		lines.add(addSaleAdjustment ("Apple",10,20,ConstSet.ADJ_ADD,5));
		lines.add(addSale ("Orange",14,1));
		lines.add(addSale ("Orange",3));
		lines.add(addSaleAdjustment ("Orange",17,2,ConstSet.ADJ_SUB,1));
		lines.add(addSaleAdjustment ("Pear",22,3,ConstSet.ADJ_ADD,3));
	
		lines.add(addSale ("Apple",10,20));
		lines.add(addSale ("Apple",15));
		lines.add(addSale ("Orange",7,7));
		lines.add(addSaleAdjustment ("Melon",10,4,ConstSet.ADJ_SUB,5));
		lines.add(addSale ("Melon",12,3));
		lines.add(addSaleAdjustment ("Grapes",8,4,ConstSet.ADJ_MUL,2));
		lines.add(addSale ("Pineapple",14,1));
		lines.add(addSale ("Tomato",2));
		lines.add(addSaleAdjustment ("Pear",17,2,ConstSet.ADJ_SUB,1));
		lines.add(addSaleAdjustment ("Banana",13,5,ConstSet.ADJ_MUL,3));

		lines.add(addSale ("Passion",9,2));
		lines.add(addSale ("Kiwi",15));
		lines.add(addSale ("Mango",7,7));
		lines.add(addSaleAdjustment ("Pineapple",11,2,ConstSet.ADJ_ADD,3));
		lines.add(addSale ("Pineapple",4,2));
		lines.add(addSaleAdjustment ("Apple",10,2,ConstSet.ADJ_ADD,15));
		lines.add(addSale ("Guava",18,1));
		lines.add(addSale ("Star",8));
		lines.add(addSaleAdjustment ("Mango",17,1,ConstSet.ADJ_SUB,1));
		lines.add(addSaleAdjustment ("Kiwi",13,5,ConstSet.ADJ_MUL,3));
}

	@Override
	public String readln() {
	
		if (count<lines.size())
			return lines.get(count++);
			return null;
	}
	
	public String addSale (String product, int amount, int qty)
	{
		return 	ConstSet.TYPE_HEADER + ConstSet.TYPE_SALE + "#" +
				ConstSet.PARAM_PRODUCT + "=" + product + "#" + 
				ConstSet.PARAM_AMOUNT + "=" + amount + "#" + 
				ConstSet.PARAM_QTY + "=" + qty ;
	}

	public String addSale (String product, int amount)
	{
		return 	ConstSet.TYPE_HEADER + ConstSet.TYPE_SALE + "#" +
				ConstSet.PARAM_PRODUCT + "=" + product + "#" + 
				ConstSet.PARAM_AMOUNT + "=" + amount ; 

	}
	public String addSaleAdjustment (String product, int amount, int qty, String adjustment, int value)
	{
		return 	
				ConstSet.TYPE_HEADER + ConstSet.TYPE_SALE + "#" +
				ConstSet.PARAM_PRODUCT + "=" + product + "#" + 
				ConstSet.PARAM_AMOUNT + "=" + amount + "#" + 
				ConstSet.PARAM_QTY + "=" + qty + "#" + 
				ConstSet.TYPE_HEADER + ConstSet.TYPE_ADJUSTMENT + "#" + 
				ConstSet.PARAM_ADJUSTMENT + "=" + adjustment +  "#" + 
				ConstSet.PARAM_PRODUCT + "=" + product + "#" + 
				ConstSet.PARAM_AMOUNT + "=" + value;
	}
	public String addSaleAdjustment (String product, int amount,  String adjustment, int value)
	{
		return 	
				ConstSet.TYPE_HEADER + ConstSet.TYPE_SALE + "#" +
				ConstSet.PARAM_PRODUCT + "=" + product + "#" + 
				ConstSet.PARAM_AMOUNT + "=" + amount + "#" + 
				ConstSet.TYPE_HEADER + "=" + ConstSet.TYPE_ADJUSTMENT + "#" + 
				ConstSet.PARAM_ADJUSTMENT + adjustment +  "#" +
				ConstSet.PARAM_PRODUCT + "=" + product + "#" + 
				ConstSet.PARAM_AMOUNT + "=" + value;
	}

}
