package main;

import databasequeries.Query;
import databaserecommendations.Recommendation;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import org.json.simple.JSONArray;
import fileio.InputLoader;
import fileio.Input;
import fileio.Writer;
import fileio.ActionInputData;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import init.CopyData;
import init.DataBase;
import databasecommands.Command;
import java.util.List;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        CopyData copyData = new CopyData();
        DataBase dataBase = copyData.copy(input);
        List<ActionInputData> actionInput = input.getCommands();

        for (ActionInputData inputData : actionInput) {
            String message = "";
            String actionType = inputData.getActionType();
            int id = inputData.getActionId();
            if (actionType.equals("command")) {
                String commandType = inputData.getType();

                message = Command.findCommand(id, dataBase, input, commandType);
            } else if (actionType.equals("recommendation")) {
                String recommendationType = inputData.getType();

                message = Recommendation.findRecommendation(
                        id, dataBase, input, recommendationType);
            } else if (actionType.equals("query")) {
                String objectType = inputData.getObjectType();
                Integer number = inputData.getNumber();
                List<List<String>> filters = inputData.getFilters();
                String sortType = inputData.getSortType();
                String criteria = inputData.getCriteria();

                Query query = new Query();
                message = query.findQueryType(
                        objectType, number, filters, sortType, criteria, id, dataBase, input);
            }
            arrayResult.add(fileWriter.writeFile(id, null, message));
        }
        fileWriter.closeJSON(arrayResult);
    }
}
