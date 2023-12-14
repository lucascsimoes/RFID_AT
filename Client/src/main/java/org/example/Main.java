package org.example;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static Gson gson = new Gson();

    public static void main(String[] args) {

        JFrame frame = new JFrame("Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6,2));

        JTextField code = new JTextField();
        code.setFont((new Font("Arial", Font.PLAIN, 30)));

        JLabel label1 = new JLabel("Código:");
        label1.setFont((new Font("Arial", Font.BOLD, 30)));

        panel.add(label1);
        panel.add(code);
        label1.setBackground(null);

        code.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    String response = sendRequest(code.getText());

                    if (code.getText().equals("")) {
                        panel.setBackground(null);
                    } else {
                        if (response.equals("ACK")) {
                            panel.setBackground(Color.GREEN);

                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    SwingUtilities.invokeLater(() -> {
                                        panel.setBackground(null);
                                    });
                                }
                            }, 3000);
                        } else {
                            panel.setBackground(Color.RED);

                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    SwingUtilities.invokeLater(() -> {
                                        panel.setBackground(null);
                                    });
                                }
                            }, 3000);
                        }
                    }
                }
                catch (Exception er){
                    System.out.println(er);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    String response = sendRequest(code.getText());

                    if (code.getText().equals("")) {
                        panel.setBackground(null);
                    } else {
                        if (response.equals("ACK")) {
                            panel.setBackground(Color.GREEN);

                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    SwingUtilities.invokeLater(() -> {
                                        panel.setBackground(null);
                                    });
                                }
                            }, 3000);
                        } else {
                            panel.setBackground(Color.RED);

                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    SwingUtilities.invokeLater(() -> {
                                        panel.setBackground(null);
                                    });
                                }
                            }, 3000);
                        }
                    }
                }
                catch (Exception er){
                    System.out.println(er);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("Alterado");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }


    public static String sendRequest(String code) {
        try {
            String apiUrl = "http://localhost:8080/permissao";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String json = gson.toJson(new Data(code));

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }

            reader.close();
            connection.disconnect();

            Gson gson = new Gson();
            Response teste = gson.fromJson(response.toString(), Response.class);

            return teste.getValue();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static class Data {
        String code;

        Data(String code) {
            this.code = code;
        }
    }

    public static class Response {
        String response;

        public String getValue() {
            return response;
        }

        Response(String response) {
            this.response = response;
        }
    }

}