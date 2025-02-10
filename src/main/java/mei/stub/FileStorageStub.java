package mei.stub;

import mei.fileaccess.FileStorage;

/**
 * Represents a stub class for the file storage.
 * Used for testing classes dependent on this in isolation.
 */
public class FileStorageStub extends FileStorage {

    public FileStorageStub(String fileStorePath) {
        super(fileStorePath);
    }

}
