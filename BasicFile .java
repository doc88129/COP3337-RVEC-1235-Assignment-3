import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class BasicFile {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String userInput = "1";

        while (userInput.compareTo("0") != 0) {
            System.out.println("Select an option: ");
            System.out.println("1. Copy file");
            System.out.println("2. Append to file");
            System.out.println("3. Overwrite file");
            System.out.println("4. Get file properties");
            System.out.println("5. Get file contents");
            System.out.println("6. Search file contents");
            System.out.println("0. Exit");
            System.out.println();

            userInput = scanner.nextLine();
            if (userInput.compareTo("1") == 0) {
                CopyFile();
            } else if (userInput.compareTo("2") == 0) {
                AppendToFile();
            } else if (userInput.compareTo("3") == 0) {
                OverwriteFile();
            } else if (userInput.compareTo("4") == 0) {
                GetFileProperties();
            } else if (userInput.compareTo("5") == 0) {
                GetFileContents();
            } else if (userInput.compareTo("6") == 0) {
                SearchFileContents();
            } else if (userInput.compareTo("0") == 0) {
                System.exit(0);
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }

        scanner.close();
    }

    public static File GetSelectedFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.CANCEL_OPTION) {
            System.exit(1);
        }

        return fileChooser.getSelectedFile();
    }

    public static File GetSelectedDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.CANCEL_OPTION) {
            System.exit(1);
        }

        return fileChooser.getSelectedFile();
    }

    public static File GetSelectedFileOrDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.CANCEL_OPTION) {
            System.exit(1);
        }

        return fileChooser.getSelectedFile();
    }

    public static void CopyFile() throws IOException { 
        File file = GetSelectedFile();
        Path path = GetSelectedDirectory().toPath();
        Files.copy(file.toPath(), path.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void AppendToFile() throws IOException {
        File file = GetSelectedFile();
        String userInput = JOptionPane.showInputDialog("Enter text to append to file: ");

        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(userInput);
        fileWriter.close();
    }

    public static void OverwriteFile() throws IOException { 
        File file = GetSelectedFile();
        String userInput = JOptionPane.showInputDialog("Enter text to write to file: ");

        FileWriter fileWriter = new FileWriter(file, false);
        fileWriter.write(userInput);
        fileWriter.close();
    }

    public static void GetFileProperties() throws IOException { 
        File file = GetSelectedFileOrDirectory();
        String outputString = "";

        outputString += "File path: " + file.getAbsolutePath() + "\n";
        outputString += "File size: " + file.length() / 1000.0 + " kilobytes\n";

        if (file.isFile()) {
            outputString += "Number of lines:" + Files.lines(file.toPath()).count() + "\n";
        } else {
            String[] files = file.list();
            outputString += "Files in directory: \n";
            if (files != null) {
                for (String f : files) {
                    outputString += "\t" + f + "\n";
                }
            } else {
                outputString += "\t" + "No files in path\n";
            }
        }

        JOptionPane.showMessageDialog(null, outputString, "File Properties", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void GetFileContents() { 
        File file = GetSelectedFile();
        String outputString = "";

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                outputString += scanner.nextLine() + "\n";
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        JOptionPane.showMessageDialog(null, outputString, "File Content", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void SearchFileContents() { 
        File file = GetSelectedFile();
        String userInput = JOptionPane.showInputDialog("Enter text to search for: ");
        String outputString = "";
        long lineNumber = 0;

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();
                if (line.toLowerCase().contains(userInput.toLowerCase())) {
                    outputString += lineNumber + ": " + line + "\n";
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        JOptionPane.showMessageDialog(null, outputString, "Filtered File Content", JOptionPane.INFORMATION_MESSAGE);

    }
}