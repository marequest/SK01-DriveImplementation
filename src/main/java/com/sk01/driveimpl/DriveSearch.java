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
    public void getFiles(String extension) {

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
    public void getFiles(File dir, Time pocetak, Time kraj) {

    }

    @Override
    public void filtrate(String string) {

    }
}
