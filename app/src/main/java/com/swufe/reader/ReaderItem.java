package com.swufe.reader;

public class ReaderItem {
    private String Reader_Name;
    private String Password;
    private String Books;

    public ReaderItem(String reader_Name, String password,String books) {
        Reader_Name = reader_Name;
        Password = password;
        Books = books;
    }

    public ReaderItem() {
        Reader_Name = "";
        Password = "";
        Books = "";
    }

    public String getReader_Name() {
        return Reader_Name;
    }

    public void setReader_Name(String reader_Name) {
        Reader_Name = reader_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

   public String getBooks() {
        return Books;
    }

    public void setBooks(String books) {
        Books = books;
    }
}
