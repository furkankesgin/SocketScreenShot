import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;


public class server_frame extends JFrame implements ActionListener{
	
	public final static int FILE_SIZE = 6022386; 

	//buttons
		JButton btnScreenShot =  new JButton("ScreenShot");
		server_frame(){
			super("ScreenShot");
			button();
		}
		void button() {

			add(btnScreenShot);
			btnScreenShot.setBounds(1,10,100,100);
			btnScreenShot.addActionListener(this);		
			btnScreenShot.setFocusable(false);

		}
		
		void get_file() {
			
			String dir = createDir();
			String file_location_and_name = CheckDirForExist(dir,"png");
			
			recieve_file(file_location_and_name);
			
		}

		void recieve_file(String recieved_file_path)  {


			try {
				ServerSocket serverSocket = new ServerSocket(4444);

				try {
					Socket socket = serverSocket.accept();

					System.out.println("Connected");

					int current_downloaded = 0;
					int bytesRead = 0;


					// receive file
					byte[] mybytearray = new byte[FILE_SIZE];
					InputStream is = socket.getInputStream();
					FileOutputStream fos = new FileOutputStream(recieved_file_path);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					bytesRead = is.read(mybytearray, 0, mybytearray.length);
					current_downloaded = bytesRead;

					do {
						bytesRead = is.read(mybytearray, current_downloaded, (mybytearray.length - current_downloaded));
						if (bytesRead >= 0) {
							current_downloaded += bytesRead;
						}

					} while (bytesRead > -1);

					fos.write(mybytearray, 0, current_downloaded);
					fos.flush();
					System.out.println("File " + recieved_file_path + " downloaded (" + current_downloaded + " bytes read)");


					fos.close();
					bos.close();

					socket.close();
					serverSocket.close();

				}

				catch(IOException e) {
					System.out.println("IO EXCEPTiON");	

				}
			}
			catch(IOException e) {
				System.out.println("IO EXCEPTiON");	
			}

		}
		String createDir() {

			//default creating point is root of the program
			File folder = new File("ScreenShotsSocket");


			// if the directory does not exist, create it
			if (!folder.exists()) {
				System.out.println("creating directory: " + folder.getName());
				try{
					folder.mkdir();
					System.out.println("DIR created"); 
				} 
				catch(SecurityException e){
					System.out.println("DIR is not created"); 
				}        
			}

			return folder.toString();
		}

		String CheckDirForExist(String theDir, String file_type) {

			int counter = 0;

			//loop until a possible counter file name
			while(true) {

				//directory + basefilename + counter + filetype from combobox 
				File file = new File(theDir + File.separator + "screenshot" + counter + "." + file_type);
				if(!file.exists()) { 
					return theDir + File.separator + "screenshot" + counter + "." + file_type;
				}
				else {
					counter++;
				}

			}
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnScreenShot) {
				get_file();
			}
		}
		
	}
