import java.util.*;
	import java.io.*;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	
	public class Local_Spider {
		static int index = 0;
		static int found = 0;
		String EnteredData[]; 
		Others add;
		static String SearchWord;
		
	
		private class Store implements Runnable {
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Map<String, String>> Text = new HashMap<String, Map<String, String>>();
			private Others queue;
	
	
			public Store(Others q) {
				queue = q;
			}
	
			public void run() {
	
				String file_Name;
				
				while ((file_Name = queue.delete()) != null) {
					File F_path = new File(file_Name);
					EnteredData =  F_path.list();
					
					if (EnteredData == null)
						continue;
					
					for(int j=0;j<EnteredData.length;j++) {
						
						if (EnteredData[j].compareTo(".") == 0)
							continue;
						if (EnteredData[j].compareTo("..") == 0)
							continue;
	
						String fn = file_Name + "\\" + EnteredData[j];
						map.put(EnteredData[j],fn);		
						
						
					}
				}
	
				for (Map.Entry<String, String> index : map.entrySet()) {
					String key = index.getKey();
					String File_Path = index.getValue();
					System.out.println("File SearchWord = " + key);
					System.out.println("File path = " + File_Path);
					System.out.println();
					}
			
				for (Map.Entry<String, String> index : map.entrySet()) {
					String key = index.getKey();
					String File_Path = index.getValue();
						if(key.contains(".txt") || key.contains(".html") || key.contains(".xml") || key.contains(".pdf")  ) { 
							List<String> token = readFromFile(File_Path);		
							StringBuilder sb = new StringBuilder();	
							for (String SearchWord : token) {							//Converting list to string for storage in our map
								sb.append(SearchWord);
								sb.append(" ");
							}
	
							Text.put(key, new HashMap<String, String>());    //Store file SearchWord first
							Text.get(key).put(File_Path, sb.toString());   		//Store file path and file content then
							
							String content = Text.get(key).get(File_Path);		//Get content of a file
							if(content.contains(SearchWord)) {
								System.out.println("Your input string " + SearchWord + "  is present in file:  " + key);
								System.out.println("File SearchWord = " + key);
								System.out.println("File path = " + File_Path);
								System.out.println();
							}
	
						}
						if(key.contains(SearchWord)) {						
							//Condition to check whether a file containing given input is present in directory or not
							System.out.println("FILE is present in the directory:" + SearchWord);
							System.out.println("File SearchWord = " + key);
							System.out.println("File path = " + File_Path);
							System.out.println();
						}
				}
				
			}
	
		}
	
	    public Store createWorker() {
			return new Store(add);
		}
		public Local_Spider() {
			add = new Others();
		}
	
		
	
		
	
		public List<String> readFromFile(String file) {			
			List<String> tokens = new ArrayList<String>();		
			BufferedReader buffread = null;
			try {
	
				String Get_Line;
				buffread = new BufferedReader(new FileReader(file));
				while ((Get_Line = buffread.readLine()) != null) {
					tokens.add(Get_Line);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (buffread != null)buffread.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
	
			return tokens;
		}
	
	    public static void Searching() {
	
			Local_Spider Craw_Object = new Local_Spider();	
			Scanner in = new Scanner(System.in);	
			System.out.println("search: ");
			SearchWord = in.nextLine();
			System.out.println("You searched for: " +SearchWord);
	
	
		
			ArrayList<Thread> tr = new ArrayList<Thread>(5);
			for (int index = 0; index < 5; index++) {
				Thread t = new Thread(Craw_Object.createWorker());
				tr.add(t);
				t.start();
			}

			Craw_Object.processDirectory("F://documents//6th Semester//Advanced Programming");
			Craw_Object.add.finish();
	
			for (int index = 0; index < 5; index++){
				try {
					tr.get(index).join();
				} catch (Exception e) {};
			}
		}
	
		public void processDirectory(String directory) {
	
			try{
	
				File file = new File(directory);
				if (file.isDirectory()) {
					String EnteredData[] = file.list();
					if (EnteredData != null)
						add.add(directory);
	
					for (String entry : EnteredData) {
						String subdir;
						if (entry.compareTo(".") == 0)
							continue;
						if (entry.compareTo("..") == 0)
							continue;
						if (directory.endsWith("\\")) {
							subdir = directory+entry;
	
	
						}
						else {
							subdir = directory+"\\"+entry;
	
							processDirectory(subdir);
	
						}
	
					}
				}}catch(Exception e){}
		}
	
		}
