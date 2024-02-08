package org.info6205.tsp.io;

import org.info6205.tsp.core.Vertex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostProcess {
    private Map<Long, String> nodeMap;
    private List<String> rawLines;
    public PostProcess(Preprocess preprocess) {
        nodeMap = preprocess.getNodeMap();
        rawLines = preprocess.getRawLines();
    }

    public List<String> start(List<Vertex> circuit, String outputFilePath) {
        List<String> result = new ArrayList<>();
        result.add("crimeID,longitude,latitude");
        for(Vertex vertex: circuit) {
            String line = getLineData(vertex.getId());
            result.add(line);
        }

        writeToFile(outputFilePath, result);
        return result;
    }

    /**
     * Writes data given lines to file
     * @param fileName Filename with extension
     */
    private void writeToFile(String fileName, List<String> result) {
        FileHelper fh = new FileHelper();
        try {
            fh.write(fileName, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The node id is substituted with original node hash.
     * @param id raw lines that have node id and co-ordinates
     * @return substituted node id with node hash
     */
    private String getLineData(long id) {
        String[] words = nodeMap.get(id).split(",");
        return words[0].substring(words[0].length() - 5) + "," + words[1] + "," + words[2];
    }
}
