import java.util.ArrayList;



class Run{
//this is the class that will run the program and where all of the data structures will be stored etc..
/*MEMBERS HERE*/

	
ArrayList<Entities> entitieslist = new ArrayList<Entities>();//store the entities
ArrayList<Attribute> attriblist = new ArrayList<Attribute>();//store the attributes in an arrraylist
ArrayList<Permissions> permList = new ArrayList<Permissions>();//make an arraylist of permissions;
private Container cont = new Container();//holds the semi-parsed policy




/*FUNCTIONS HERE*/
/*this function executes the program*/
public  void executeCommand(String command) throws Exception{//executes commands given a string;
	String[] commandParts = command.split(" ");
	switch (commandParts[0]){

	case "load-policy": loadPolicy(commandParts[1]); break;

	case "check-permission": CheckPermission(commandParts[1], commandParts[2], commandParts[3], commandParts[4]);break;
	
	case "show-policy": showPolicy();break;
	
	case "add-entity": addEntity(commandParts[1]);break;
	
	case "remove-entity": removeEntity(commandParts[1]);break;
	
	case "add-attribute": addAttribute(commandParts[1],commandParts[2]);break;
	
	case "remove-attribute": removeAttribute(commandParts[1]);break;
	
	case "add-permission": addPermission(commandParts[1]);break;
	
	case "remove-permission" : removePermission(commandParts[1]);break;
	
	case "add-attributes-to-permission":
		String cmd  = "";//need to get all of the input as a single string again
		for(int i  = 1 ; i < commandParts.length; i++) {
			cmd = cmd + commandParts[i] + " " ;
		}
		addAttribToPerm(cmd);break;
	
	case "remove-attribute-from-permission": remAttribFromPerm(commandParts[1], commandParts[2], commandParts[3]);break;
	
	case "add-attribute-to-entity": addAttribToEntity(commandParts[1],commandParts[2],commandParts[3]);break;
	
	case "remove-attribute-from-entity": remAttribFromEntity(commandParts[1],commandParts[2],commandParts[3]);break;

	default: System.err.println("Unrecognized command!"); break;
	}
}


private void remAttribFromEntity(String entityName, String AttributeName, String AttributeValue) {
	ArrayList<Attribute> temp = new ArrayList<Attribute>();
		for(Entities e : entitieslist) {
			if(e.name.equals(entityName)) {
				for(Attribute a : e.list) {
					if(a.name.equals(AttributeName)) {
						temp.add(a);
					}
				}
			}
			e.list.removeAll(temp);
			temp.removeAll(temp);
		}
}


private void addAttribToEntity(String entityName, String attribName, String attribValue) {
	for(Entities e : entitieslist) {
		if(e.name.equals(entityName)) {
					Attribute<String> temp = new Attribute<String>();
					temp.name = attribName;
					temp.t = attribValue;
					e.list.add(temp);
			}
		}
	}
	



private void remAttribFromPerm(String permName, String attribName, String AttribValue) {
	ArrayList<Attribute> temp = new ArrayList<Attribute>();
	for(Permissions p :permList) {
		for(Attribute t :p.list) {
			if(p.name.equals(permName) && t.name.equals(attribName)) {
				temp.add(t);
			}
		}
		p.list.removeAll(temp);
		temp.removeAll(temp);
	}
	
}

private void addAttribToPerm(String cmd) {
	String parts[] = cmd.split(" ");
	Permissions temp = new Permissions();
	temp.name= parts[0];
	
	for(int i = 1 ;  i < parts.length ; i=i+2) {
		Attribute<String> at = new Attribute<String>();//create an attribute
		at.name = parts[i];//get the name
		at.t = parts[i+1];//get the value
		temp.list.add(at);//store the Attribute into the Permission
	}
	permList.add(temp);
	
}




private void removePermission(String name) {
	ArrayList<Permissions> temp = new ArrayList<Permissions>();
	for(Permissions p :permList) {
		if(p.name.equals(name)) {
			temp.add(p);
		}
	}
	permList.removeAll(temp);
}

/*adds a permission to a policy*/
private void addPermission(String name) {
	Permissions temp = new Permissions();
	temp.name = name;
	permList.add(temp);
}


/*removes an attribute from a PA as well as from the attribute List and from any entities*/
private void removeAttribute(String attribute) {
	ArrayList<Permissions> list = new ArrayList<Permissions>();
	ArrayList<Attribute> temp  =new ArrayList<Attribute>();
//	ArrayList<Entities> entities = new ArrayList<Entities>();
	for(Attribute t : attriblist) {
		if(t.name.equals(attribute)) {
			temp.add(t);//add the attribute to out temp list
		}
		
	}
	attriblist.removeAll(temp);
	attriblist.trimToSize();
	temp.removeAll(temp);
	for(Permissions p :permList) {
		for(Attribute a :p.list) {
			if(a.name.equals(attribute)) {
				temp.add(a);
			}
		}
		p.list.removeAll(temp);//remove all of the atttributes from p.list which were stored in temp
		temp.removeAll(temp);
		if(p.list.isEmpty() == true);//if the permission added made the PA empty
			list.add(p);//store it in a temp list
	}
	permList.removeAll(list);//remove all of the items that were empty which were stored in list from permlist
	for(Entities ent :entitieslist) {
		for(Attribute attrib : ent.list ) {
			if(attrib.name.equals(attribute)) {
				temp.add(attrib);
			}
		}
		ent.list.removeAll(temp);
		temp.removeAll(temp);
	}
}
	







/*adds a new attribute into a policy*/
private void addAttribute(String attributeName, String attributeType) {
	switch(attributeType){
    case "String" : Attribute<String> t0 = new Attribute<String>(attributeType, attributeName);attriblist.add(t0);break;
    case "int" : Attribute<Integer> t1 = new Attribute<Integer>(attributeType, attributeName);attriblist.add(t1) ; break; 
    case "float" : Attribute<Float> t2 = new Attribute<Float>(attributeType, attributeName);attriblist.add(t2); break; 
    case "short" : Attribute<Short> t3 = new Attribute<Short>(attributeType, attributeName);attriblist.add(t3); break;
    case "byte" : Attribute<Byte> t4 = new Attribute<Byte>(attributeType, attributeName);attriblist.add(t4); break;
    case "long" : Attribute<Long> t5 = new Attribute<Long>(attributeType, attributeName);attriblist.add(t5); break;
    case "char" : Attribute<Character> t6 = new Attribute<Character>(attributeType, attributeName);attriblist.add(t6); break;
    case "boolean" : Attribute<Boolean> t7 = new Attribute<Boolean>(attributeType, attributeName);attriblist.add(t7); break;
    default: Attribute<String> temp = new Attribute<String>(attributeType, attributeName);attriblist.add(temp); break; //if we do not have a type for it it will most likely just be a string
    
	}
}
/*finds and removes an element from a policy given a name */
private void removeEntity(String entity_name) {
	int x = findEntitiy(entity_name);
	entitieslist.remove(x);
	entitieslist.trimToSize();
	
}
/*adds a new entity to the policy*/
private void addEntity(String entity_name) {
	Entities temp = new Entities(entity_name);
	entitieslist.add(temp);
	
}

public void loadPolicy(String FileName){
    parse_Policy(FileName);
    initAttribList();//initialize the arraylists of the attributes
    PA();//assign permissions with attributes
    initEntities();
    AA();//assign entities with attributes
}

private  Container parse_Policy(String fileName){//this is where we are going to read in the file and store all of the policy info into a container obj
    Reader r = new Reader();
    
    r.openFile(fileName);
    String[] policy = r.readFile();
    int i = 0;
    for(String command : policy){
        command = command.trim();
        switch(i){
            case 0: cont.s0 = r.parse_attributes(command); break;
            case 1: cont.s1 = r.parse_perms(command); break;
            case 2: cont.s2 = r.parse_PA(command); break;
            case 3: cont.s3 = r.parse_Entities(command); break;
            case 4: cont.s4 = r.parse_AA(command);break;
           default: break;
        }
            

        i++;
    }
    r.closeFile();
    return cont;//returned the semi parsed policy
}//ends method
//the method initializes all of the attributes objects to whatever type the are doing to be 
private void initAttribList(){
    int i  = 0;
    while(i < cont.s0.length){//while we havent finished looping through the policy create the object and store the name and type
    switch(cont.s0[i]){
        case "String" : Attribute<String> t0 = new Attribute<String>(cont.s0[i], cont.s0[i+1]);attriblist.add(t0);break;
        case "int" : Attribute<Integer> t1 = new Attribute<Integer>(cont.s0[i], cont.s0[i+1]);attriblist.add(t1) ; break; 
        case "float" : Attribute<Float> t2 = new Attribute<Float>(cont.s0[i], cont.s0[i+1]);attriblist.add(t2); break; 
        case "short" : Attribute<Short> t3 = new Attribute<Short>(cont.s0[i], cont.s0[i+1]);attriblist.add(t3); break;
        case "byte" : Attribute<Byte> t4 = new Attribute<Byte>(cont.s0[i], cont.s0[i+1]);attriblist.add(t4); break;
        case "long" : Attribute<Long> t5 = new Attribute<Long>(cont.s0[i], cont.s0[i+1]);attriblist.add(t5); break;
        case "char" : Attribute<Character> t6 = new Attribute<Character>(cont.s0[i], cont.s0[i+1]);attriblist.add(t6); break;
        case "boolean" : Attribute<Boolean> t7 = new Attribute<Boolean>(cont.s0[i], cont.s0[i+1]);attriblist.add(t7); break;
        default: Attribute<String> temp = new Attribute<String>(cont.s0[i], cont.s0[i+1]);attriblist.add(temp); break; //if we do not have a type for it it will most likely just be a string
        
    }
    i = i+2;
    }
}

private  void PA(){
   cont.s2 = cont.s2.replaceFirst("PA = ", "");
   for(int x = 0; x <cont.s1.length; x++){//place square brackets around the permissions
   cont.s2 = cont.s2.replaceAll(cont.s1[x], "[" + cont.s1[x] + "]");
   }
   cont.s2 = cont.s2.replaceAll("<|\\>|\\ |\\-|:", "");//remove unneeded characters
   cont.s2 = cont.s2.replaceAll("\"", "");//removes quotation marks from string
   String[] s3 = cont.s2.split("]");//split on closing parenthesis
   
   
   for(int j = 0 ; j<s3.length ;j++){
        String[] temp = s3[j].split(",|\\[|\\;");//split into each other permissions
        Permissions p  = new Permissions();//make a new permissions object
        p.name  = temp[temp.length-1];//assign the name of the temp permission
        int x = 0 ; 
        while(x <= temp.length-2) {//while we haven't finished parsing the permission in question
        	Attribute<String> attrib = new Attribute<String>();
        	attrib.name = temp[x];//store the name of the attribute
        	attrib.t = temp[x+1];//store the value of the attribute
        	x = x +2 ;
        	p.list.add(attrib);//add the attribute to the Permission object
        }
       permList.add(p);//add the permission to the permission list
}


}

/*utility function that prints the permission assign*/
public void printPermission() {
	for(Permissions  p  : permList) {
		System.out.println(p.name);//
		for(Attribute<String> t  : p.list){
			System.out.println("Name : " + t.name + " Value : " + t.t);
		}
		System.out.println("\n");
	}
}

/*creates the entities we will need*/
private void initEntities() {
	for(String s : cont.s3) {
		Entities temp = new Entities(s);//create a local entities variable
		entitieslist.add(temp);//add the entity to the list
	}
}

private void AA() {
	String s =  cont.s4.replaceFirst("AA = ", "");
	String[] ent = s.split(";");
	for(String str : ent) {
		str = str.trim();
		String regex = "<|\\>|\"|\\ ";//regular expression
		str = str.replaceAll(regex, "");
		String[] array = str.split(":|\\,");//split the string up again
		int x = findEntitiy(array[0]);//find the entity 
		Attribute<String> attribute = new Attribute<String>();
		attribute.name = array[1];
		attribute.t = array[2];
		entitieslist.get(x).list.add(attribute);//add the attribute to the entity
	}
}

/*utility function to find the index of an entity given a name*/
private int findEntitiy(String str) {
	int x = 0;
	for(Entities iter : entitieslist) {
		if(iter.name.equals(str)) {
			return x;
		}
		x++;
	}
	return -1;
}

/*utility function to print out a policy */
public void showPolicy(){
	for(Attribute a : attriblist) {
		System.out.println("Attribute: " + a.name +" type: " + a.type);
		
	}
	System.out.println("-----------------------");
	for(Permissions p : permList) {
		System.out.println(p.name);
		for(Attribute att : p.list) {
			System.out.println("Name : "+ att.name + " Value : "+ att.t );
		}
		System.out.println("");
	}
	System.out.println("----------------------");
	for(Entities t :entitieslist) {
		System.out.println(t.name);
		for(Attribute at : t.list) {
			System.out.println("attribute : " + at.name + " value : " + at.t);
		}
		System.out.println("");
	}
	System.out.println("--------------");
}
	

public void CheckPermission(String userName,String objectName,String enviromentName,String Permission) {
	ArrayList<Permissions> plist  = getPermissionList(Permission);//get all of the permissions
	Entities  user = new Entities();
	Entities obj = new Entities();
	Entities env = new Entities();
	user = entitieslist.get(findEntitiy(userName));//we should get all of the users
	obj = entitieslist.get(findEntitiy(objectName));
	env = entitieslist.get(findEntitiy(enviromentName));
	AttributeBag bag = new AttributeBag(user,obj,env);//create a bag to hold all of the attributes
	
	for(Permissions p :plist) {
		int count  = 0;
		for(Attribute attribute : p.list) {
			count = count + hasAttribute(attribute ,bag);
		}
		if(count == p.list.size()) {//if the count is the number of attributes matches matches the list size return true
			System.out.println("Permission GRANTED!");
			return;
		}
	}
	System.out.println("Permission DENIED!");
	
}

/*this method gets all of the permissions of with the correct name and returns a list
 * with all of the PA and their attributes*/
private ArrayList<Permissions> getPermissionList(String Permission){
	ArrayList<Permissions> temp = new ArrayList<Permissions>();
	for(Permissions p : permList) {
		if(p.name.equals(Permission)) {
			temp.add(p);
		}
	}
	return temp;
}

/*method returns an arraylist of Attributes given an entity*/

//given an attribute see if it is in the attribute is in the bag
private int hasAttribute(Attribute att , AttributeBag bag) {
	for(Attribute t : bag.list) {
		if(t.name.equals(att.name)  && t.t.equals(att.t)) {
			return 1;
		}
	}
	return 0;
}



//////////////////////////////////////////////////////////////////////


}//ends class


class Attribute<T>{
    String name;
     String type;
     T t;
    Attribute(String type , String name ){ this.type = type; this.name = name; ; this.t = null;}
	public Attribute() {//default constructor
		
	}
  
}

class Permissions{
    String name ;
    ArrayList<Attribute> list = new ArrayList<Attribute>();
}

class Entities{
    String name;
    ArrayList<Attribute> list = new ArrayList<Attribute>();
    Entities(String str){this.name = str;}
	public Entities() {
	}
    
}

class AttributeBag {
	ArrayList<Attribute> list = new ArrayList<Attribute>();
	public AttributeBag(Entities user , Entities object , Entities envir){//constructor
		this.list.addAll(user.list);
		this.list.addAll(object.list);
		this.list.addAll(envir.list);
	}
}

