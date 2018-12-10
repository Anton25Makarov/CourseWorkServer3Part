package by.bsuir.course.window;

import by.bsuir.course.database.DataBaseData;
import by.bsuir.course.database.DataBaseWorker;
import by.bsuir.course.database.FileReaderWriter;
import by.bsuir.course.entities.Referee;
import by.bsuir.course.entities.Sportsman;
import by.bsuir.course.server.ServerConfigurator;
import by.bsuir.course.server.ServerProperties;
import by.bsuir.course.server.ServerThread;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainWindow extends JFrame {
    private static final int ACTIVE_PORT = 8071;
    private InetAddress addr;
    private int backlog;
    private int port;


    private JPanel panel;
    private JButton startButton;
    private JButton endButton;
    private JButton loadToReserv;
    private JButton getFromReserv;
    private JLabel socketStatus;
    private JLabel activePort;
    private JTextArea connectedList;

    private boolean serverWork;
    private ExecutorService executorService;
    private ServerSocket serverSocket;

    public MainWindow() throws HeadlessException {
        super("Сервер");
        setSize(500, 550);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        init();

        Runnable startServer = () -> {
            try {
                setServerParameters();
                serverSocket = new ServerSocket(port, backlog, addr);
                while (serverWork) {
                    System.out.println("wait connection ...");

                    Socket sock = serverSocket.accept();
                    System.out.println(sock.getInetAddress().getHostName() + " connected");
                    connectedList.append("Подключился: " + sock.getInetAddress() +
                            "-" + sock.getPort() + " - " + (new Date()).toString() + "\n");

                    ServerThread server = new ServerThread(sock);
                    server.start();//запуск потока
                }
            } catch (SocketException e) {
                socketStatus.setText("Сокет закрыт");
                System.out.println("closed");
            } catch (IOException e) {
                e.getStackTrace();
            }
        };

        startButton.addActionListener(event -> {
            if (!serverWork) {
                executorService = Executors.newSingleThreadExecutor();
                serverWork = true;
                executorService.execute(startServer);
                socketStatus.setText("Сокет открыт");
            }
        });

        endButton.addActionListener(event -> {
            if (serverWork) {
                serverWork = false;
                executorService.shutdown();
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        loadToReserv.addActionListener(e -> {
            DataBaseWorker dataBaseWorker = DataBaseWorker.getInstance();
            java.util.List<Referee> referees = dataBaseWorker.readReferees();
            java.util.List<Sportsman> sportsmen = dataBaseWorker.readSportsmen(referees);

            DataBaseData dataBaseData = new DataBaseData(sportsmen, referees);

            FileReaderWriter fileWriter = new FileReaderWriter();

            fileWriter.setDbData(dataBaseData);
        });

        getFromReserv.addActionListener(e -> {
            DataBaseData dataBaseData;

            FileReaderWriter fileWriter = new FileReaderWriter();

            dataBaseData = fileWriter.getDbData();

            if (dataBaseData == null) {
                JOptionPane.showMessageDialog(this, "Ошибка прочтения бэкап файла");
                return;
            }

            java.util.List<Referee> referees = dataBaseData.getReferees();
            List<Sportsman> sportsmen = dataBaseData.getSportsmen();

            System.out.println(referees);
            System.out.println(sportsmen);

            DataBaseWorker dataBaseWorker = DataBaseWorker.getInstance();
            dataBaseWorker.addSportsmenAndReferees(sportsmen, referees);
        });
    }

    private void init() {
        panel = new JPanel();
        panel.setLayout(null);

        startButton = new JButton("Запуск");
        startButton.setLocation(100, 50);
        startButton.setSize(100, 50);

        endButton = new JButton("Стоп");
        endButton.setLocation(250, 50);
        endButton.setSize(100, 50);

        socketStatus = new JLabel("Сокет закрыт");
        socketStatus.setLocation(375, 450);
        socketStatus.setSize(100, 100);
        socketStatus.setBackground(Color.pink);

        activePort = new JLabel("Порт: " + ACTIVE_PORT);
        activePort.setLocation(20, 450);
        activePort.setSize(80, 100);
        activePort.setBackground(Color.pink);

        loadToReserv = new JButton("Сохранить");
        loadToReserv.setLocation(120, 450);
        loadToReserv.setSize(100, 30);

        getFromReserv = new JButton("Загрузить");
        getFromReserv.setLocation(260, 450);
        getFromReserv.setSize(100, 30);

        connectedList = new JTextArea();
        connectedList.setLocation(40, 120);
        connectedList.setSize(400, 300);
        connectedList.setEditable(false);

        panel.add(startButton);
        panel.add(endButton);
        panel.add(socketStatus);
        panel.add(activePort);
        panel.add(connectedList);
        panel.add(loadToReserv);
        panel.add(getFromReserv);


        add(panel);
    }

    private void setServerParameters() throws UnknownHostException {
        ServerConfigurator configurator = new ServerConfigurator();

        ServerProperties properties = configurator.getProperties();

        addr = InetAddress.getByName(properties.getIp());
        backlog = properties.getBacklog();
        port = properties.getPort();
    }
}
