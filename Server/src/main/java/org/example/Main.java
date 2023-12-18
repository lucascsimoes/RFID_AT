package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        port(8080);

        JFrame frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JTextField code = new JTextField();
        code.setFont((new Font("Arial", Font.PLAIN, 30)));

        JLabel label1 = new JLabel("Código:");
        label1.setFont((new Font("Arial", Font.BOLD, 30)));

        JLabel responseLabel = new JLabel("Resposta: ");
        responseLabel.setFont((new Font("Arial", Font.BOLD, 30)));
        JTextField responseValue = new JTextField();
        responseValue.setFont((new Font("Arial", Font.PLAIN, 30)));
        responseValue.setEnabled(false);

        panel.add(label1);
        panel.add(code);
        panel.add(responseLabel);
        panel.add(responseValue);

        frame.add(panel);
        frame.setVisible(true);

        post("/permissao", (request, response) -> {
            String corpoRequest = request.body();
            System.out.println("Corpo JSON: " + corpoRequest);

            JsonElement jsonElement = JsonParser.parseString(corpoRequest);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            responseValue.setText(checkValues(code.getText(), jsonObject.get("code").getAsString()));

            return "{\"response\":"+ responseValue.getText() +"}";
        });
    }

    public static String checkValues(String codeClient, String codeServer) {
        if (codeClient.length() == codeServer.length()) {
            if (codeClient.equals(codeServer)) {
                return "ACK";
            } else {
                return "NACK";
            }
        }

        return "null";
    }
}