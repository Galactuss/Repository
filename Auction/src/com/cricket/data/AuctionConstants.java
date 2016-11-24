package com.cricket.data;

import com.cricket.model.Country;

public interface AuctionConstants {
		
	public static final String EMPTY_SPACE = " ";
	
	public static final String[] TEAM_NAMES = {"Future Kings", "Speed Chasers", "Indian Superheroes", "Furious Fighters", "Universal Superstars", "Deadly Dementors", "World Champions", "Kanpur Lions"};
	
	public static final String[] COUNTRY_NAMES = {"Australia", "England", "India", "New Zealand", "Pakistan", "South Africa", "Sri Lanka", "West Indies", "Afghanistan", "Bangladesh", "Ireland", "Netherlands", "Scotland", "Zimbabwe"};
	
	public static final String[] AUSTRALIA_PLAYERS = {"GJ Bailey", "JP Faulkner", "AJ Finch", "JW Hastings", "UT Khawaja", "NM Lyon", "GJ Maxwell", "KW Richardson", "SPD Smith", "AJ Tye", "MS Wade", "DA Warner", "SR Watson", "A Zampa", "NM Coulter-Nile", "SE Marsh", "MA Starc", "JR Hazelwood"};

	public static final Country AUSTRALIA = new Country("Australia", "Overseas", AUSTRALIA_PLAYERS);
	
	public static final String[] ENGLAND_PLAYERS = {"JC Buttler", "NRD Compton", "ST Finn", "AD Hales", "EJG Morgan", "LE Plunkett", "AU Rashid", "JE Root", "JJ Roy", "JWA Taylor", "RJW Topley", "DJ Willey", "CR Woakes", "MA Wood", "MM Ali", "JM Bairstow", "SCJ Broad", "BA Stokes"};
	
	public static final Country ENGLAND = new Country("England", "Overseas", ENGLAND_PLAYERS);
	
	public static final String[] NEW_ZEALAND_PLAYERS = {"CJ Anderson", "TA Boult", "NT Broom", "DG Brownlie", "MJ McClenaghan", "BB McCullum", "NL McCullum", "AF Milne", "JDS Neesham", "MJ Santner", "TG Southee", "LRPL Taylor", "BJ Watling", "KS Williamson", "MJ Henry", "C Munro", "TWM Latham", "N Wagner"};
	
	public static final Country NEW_ZEALAND = new Country("New Zealand", "Overseas", NEW_ZEALAND_PLAYERS);
	
	public static final String[] PAKISTAN_PLAYERS = {"Khurram Manzoor", "Mohammad Amir", "Mohammad Hafeez", "Mohammad Irfan", "Mohammad Sami", "Saeed Ajmal", "Sarfraz Ahmed", "Shahid Afridi", "Sharjeel Khan", "Shoaib Malik", "Sohail Tanvir", "Umar Akmal", "Umar Gul", "Wahab Riaz", "Asad Shafiq", "Yasir Shah", "Azhar Ali", "Imad Wasim"};

	public static final Country PAKISTAN = new Country("Pakistan", "Overseas", PAKISTAN_PLAYERS);
	
	public static final String[] SOUTH_AFRICA_PLAYERS = {"HM Amla", "H Davids", "Q de Kock", "AB de Villiers", "JP Duminy", "RE Levi", "DA Miller", "JA Morkel", "CH Morris", "K Rabada", "RR Rossouw", "DW Steyn", "LL Tsotsobe", "D Wiese", "F du Plesis", "Imran Tahir", "M Morkel", "KJ Abbott"};
	
	public static final Country SOUTH_AFRICA = new Country("South Africa", "Overseas", SOUTH_AFRICA_PLAYERS);
	
	public static final String[] SRI_LANKA_PLAYERS = {"LD Chandimal", "TM Dilshan", "HMRKB Herath", "CK Kapugedera", "KMDN Kulasekara", "RAS Lakmal", "SL Malinga", "AD Mathews", "BAW Mendis", "MDKJ Perera", "NLTC Perera", "CAK Rajitha", "SMSM Senanayake", "HDRL Thirimanne", "JDF Vandersay", "N Pradeep", "BKG Mendis", "S Prasanna"};
	
	public static final Country SRI_LANKA = new Country("Sri Lanka", "Overseas", SRI_LANKA_PLAYERS);
	
	public static final String[] WEST_INDIES_PLAYERS = {"S Badree", "SJ Benn", "D Bishoo", "CR Brathwaite", "DJ Bravo", "J Charles", "CH Gayle", "JO Holder", "SP Narine", "KA Pollard", "D Ramdin", "AD Russell", "MN Samuels", "K Santokie", "DM Bravo", "TL Best", "ST Gabriel", "DJG Sammy"};
	
	public static final Country WEST_INDIES = new Country("West Indies", "Overseas", WEST_INDIES_PLAYERS);
	
	public static final String[] INDIAN_CAPPED_PLAYERS = {"S Aravind", "R Ashwin", "STR Binny", "JJ Bumrah", "S Dhawan", "MS Dhoni", "Harbhajan Singh", "RA Jadeja", "KM Jadhav", "V Kohli", "B Kumar", "A Mishra", "Mohammed Shami", "P Negi", "A Nehra", "MK Pandey", "HH Pandya", "AR Patel", "AM Rahane", "SK Raina", "AT Rayudu", "SV Samson", "Sandeep Sharma", "KV Sharma", "MM Sharma", "RG Sharma", "RV Uthappa", "M Vijay", "R Vinay Kumar", "Yuvraj Singh", "R Dhawan", "Gurkeerat Singh", "DS Kulkarni", "WP Saha", "I Sharma", "BB Sran", "MK Tiwary", "UT Yadav", "VR Aaron", "PP Chawla", "G Gambhir", "Z Khan", "NV Ojha", "PP Ojha", "Pankaj Singh", "CA Pujara", "KL Rahul", "KD Karthik", "IK Pathan", "RP Singh", "YK Pathan", "P Kumar", "AB Dinda", "S Tyagi", "P Awana", "PA Patel", "MM Patel", "AM Nayar", "A Mithun", "SS Tiwary"};
	
	public static final Country INDIA = new Country("India", "Indian", INDIAN_CAPPED_PLAYERS);
	
	public static final String[] DOMESTIC_PLAYERS = {"RK Bhui", "AN Ahmed", "BB Patel", "AA Das", "VP Rawal", "SS Bandekar", "HP Patel", "PY Tehlan", "RG More", "H Ahmed", "R Dayal", "Jaskaran Singh", "CM Gautam", "MD Nidheesh", "MD Mishra", "SD Atitkar", "SG Mangela", "J Behera", "S Ladda", "AA Nandi", "PR Yadav", "VM Jivrajani", "Yashpal Singh", "DT Chandrasekar", "R Dutta", "Almas Shaukat", "FY Fazal"};
	
	public static final Country DOMESTIC = new Country("Domestic", "Domestic", DOMESTIC_PLAYERS);
	
	public static final String[] IRELAND_PLAYERS = {"TJ Murtagh", "GH Dockrell", "PR Stirling", "WB Rankin", "GC Wilson", "KJ O'Brien", "WTS Porterfield", "MC Sorenson", "CA Young", "JF Mooney", "A Balbirnie"};
	
	public static final Country IRELAND = new Country("Ireland", "Associates", IRELAND_PLAYERS);
	
	public static final String[] AFGHANISTAN_PLAYERS = {"Gulbadin Naib", "Izatullah Dawlatzai", "Asghar Stanikzai", "Usman Ghani", "Sayed Shirzad", "Dawlat Zadran", "Noor Ali Zardan", "Karim Sadiq", "Mohammed Nabi", "Dawlat Ahmadzai", "Hamid Hassan"};
	
	public static final Country AFGHANISTAN = new Country("Afghanistan", "Associates", AFGHANISTAN_PLAYERS);
	
	public static final String[] BANGLADESH_PLAYERS = {"Mashrafe Mortaza", "Tamim Iqbal", "Mustafizur Rahman", "Saqlain Sajib", "Arafat Sunny", "Mohammad Shahid", "Mominul Haque", "Sabbir Rahman", "Imrul Kayes", "Mushfiqur Rahim", "Taskin Ahmad"};
	
	public static final Country BANGLADESH = new Country("Bangladesh", "Associates", BANGLADESH_PLAYERS);
	
	public static final String[] ZIMBABWE_PLAYERS = {"SC Williams", "H Masakadza", "MN Waller", "CB Mpofu", "E Chigumbura", "BV Vitori", "V Sibanda", "BRM Taylor", "CR Ervine", "KM Jarvis", "TL Chatara"};
	
	public static final Country ZIMBABWE = new Country("Zimbabwe", "Associates", ZIMBABWE_PLAYERS);
	
	public static final String[] NETHERLANDS_PLAYERS = {"JF Mooney", "PM Seelaar", "PA van Meekeren", "LV van Beek", "RE van der Merwe", "MR Swart", "SJ Myburgh", "Peter Borren", "TW Cooper", "W Barresi", "M Bhukari"};
	
	public static final Country NETHERLANDS = new Country("NetherLands", "Associates", NETHERLANDS_PLAYERS);
	
	public static final String[] SCOTLAND_PLAYERS = {"RD Berrington", "KJ Coetzer", "AC Evans", "MW Machan", "SM Sharif", "PL Mommsen", "MH Cross", "CS McLeod", "MRJ Watt", "CD de Lange", "HG Munsey"};
	
	public static final Country SCOTLAND = new Country("Scotland", "Associates", SCOTLAND_PLAYERS);
		
	public static final Country[] COUNTRIES = {AUSTRALIA, ENGLAND, NEW_ZEALAND, PAKISTAN, SOUTH_AFRICA, SRI_LANKA, WEST_INDIES}; 
	
	public static final Country[] ASSOCIATE_COUNTRIES = {AFGHANISTAN, BANGLADESH, IRELAND, NETHERLANDS, SCOTLAND, ZIMBABWE};
	
	public static final String DATA_DIR = "C:\\Users\\Pushpak\\data\\";
	
	public static final String AUCTION_FILE_NAME = "\\AuctionFile.txt";
	
	public static final String TEAM_FILE_NAME = "\\TeamFile.txt";
			
}
