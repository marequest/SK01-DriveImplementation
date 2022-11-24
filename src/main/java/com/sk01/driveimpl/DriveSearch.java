package com.sk01.driveimpl;

import com.sk01.storage.Search;

import java.io.File;
import java.sql.Time;
import java.util.List;

public class DriveSearch extends Search {
    @Override
    public void getFiles(File dir) {

    }

    @Override
    public void getFiles(String podstring, String name) {

    }

    @Override
    public List<File> getFile(String path) throws Exception {
        return null;
    }

    @Override
    public void getFiles(String extension) {

    }

    @Override
    public List<File> getFilesWithExtension(String extension) throws Exception {
        return null;
    }

    @Override
    public List<File> getAllFiles(String path) throws Exception {
        return null;
    }

    @Override
    public boolean containsFiles(String path, List fileNames) throws Exception {
        return false;
    }

    @Override
    public void getAllFiles(File dir) {

    }

    @Override
    public boolean containsFiles(File dir, List fileNames) {
        return false;
    }

    @Override
    public File getDir(String fileName) {
        return null;
    }

    @Override
    public void sortByName(List files) {

    }

    @Override
    public void sortByDate(List files) {

    }

    @Override
    public List<File> getFiles(String path, Time pocetak, Time kraj) throws Exception {
        return null;
    }

    @Override
    public void getFiles(File dir, Time pocetak, Time kraj) {

    }

    @Override
    public void filtrate(String string) {

    }
}
