package com.sk01.driveimpl;

import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.File;
import com.sk01.storage.Search;
import com.sk01.utils.StorageInfo;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DriveSearch extends Search {


    @Override
    public java.io.File getFile(String path) throws Exception {
        return null;
    }

    @Override
    public List<java.io.File> getFiles(String podstring) throws Exception {
        return null;
    }

    @Override
    public List<java.io.File> getFilesWithExtension(String extension) throws Exception {
        return null;
    }

    @Override
    public List<java.io.File> getAllFiles(String path) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;

        File dir = GoogleDrive.getFile(path);
        String query = "parents=" + "'" + dir.getId() + "'";  //nalazimo decu preko parent id-a
        FileList list = GoogleDrive.service.files().list().setQ(query).setFields("nextPageToken, files(id, name, createdTime, mimeType, modifiedTime, parents, fileExtension)").execute();

        List<File> files = new ArrayList<>();
        for (File file: list.getFiles()) {
            if (!file.getMimeType().equals("application/vnd.google-apps.folder")) {
                files.add(file);
            }
        }

        return getJavaFiles(files);
    }

    @Override
    public boolean containsFiles(String path, List fileNames) throws Exception {
        return false;
    }

    @Override
    public String getDir(String fileName) throws Exception {
        return null;
    }


    @Override
    public List<java.io.File> sortByName(List files) throws Exception {
        return null;
    }

    @Override
    public List<java.io.File> sortByDate(List files) throws Exception {
        return null;
    }

    @Override
    public List<java.io.File> getFiles(String path, Time pocetak, Time kraj) throws Exception {
        return null;
    }

    @Override
    public List<java.io.File> filtrate(String string) throws Exception {
        return null;
    }

    private List<java.io.File> getJavaFiles(List<File> files) {
        List<java.io.File> javaFiles = new ArrayList<>();

        for (File file: files) {
            String name = file.getName();

            java.io.File newFile = new java.io.File(name);
            javaFiles.add(newFile);
        }

        return javaFiles;
    }
}
