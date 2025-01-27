package stub;

import manager.ResponseManager;
import manager.TaskManager;

public class ResponseManagerStub extends ResponseManager {
    /**
     * The constructor.
     * Initializes a response map which maps the response type to its array of responses.
     * The response map is given the final modifier as no modifications should be made upon initialization.
     *
     * @param taskManager
     */
    public ResponseManagerStub(TaskManager taskManager) {
        super(taskManager);
    }
}
