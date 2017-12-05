package maze;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

public class MazeApp extends JFrame{
  private JLabel statusbar;
  private String mazeFile = "";
  private Maze maze;
  private String agenda = "A*";
  private MazeSolver solver;
  private boolean isStarted = false;

  public MazeApp(){
    initUI();
  }

  private void initUI(){
    createMenuBar();
    statusbar = new JLabel("status");
    add(statusbar, BorderLayout.SOUTH);
    maze = new Maze();
    add(maze, BorderLayout.CENTER);

    setTitle("Maze App");
    setSize(600, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  private void createMenuBar() {
    JMenuBar menubar = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenuItem load = new JMenuItem("Load Maze");
    load.setToolTipText("Load a new maze configuration");
    load.addActionListener(new OpenFileAction());
    JMenuItem solve = new JMenuItem("Solve Maze");
    solve.setToolTipText("Solve the current maze");
    solve.addActionListener(new SolveMazeAction());

    file.add(load);
    file.add(solve);
    menubar.add(file);

    JMenu agendas = new JMenu("Agenda");
    ButtonGroup group = new ButtonGroup();

    JRadioButtonMenuItem astar = new JRadioButtonMenuItem("A*");
    astar.setSelected(true);
    agendas.add(astar);

    astar.addItemListener(new ItemListener(){
      public void itemStateChanged (ItemEvent e){
        if (!  isStarted && e.getStateChange() == ItemEvent.SELECTED){
          agenda = "A*";
          statusbar.setText("use A* agenda");
        }
      }
    });

    JRadioButtonMenuItem stack = new JRadioButtonMenuItem("Stack");
    stack.setSelected(true);
    agendas.add(stack);

    stack.addItemListener(new ItemListener(){
      public void itemStateChanged (ItemEvent e){
        if (!  isStarted && e.getStateChange() == ItemEvent.SELECTED){
          agenda = "Stack";
          statusbar.setText("use stack agenda");
        }
      }
    });

    JRadioButtonMenuItem queue = new JRadioButtonMenuItem("Queue");
    queue.setSelected(true);
    agendas.add(queue);

    queue.addItemListener(new ItemListener(){
      public void itemStateChanged (ItemEvent e){
        if (!  isStarted && e.getStateChange() == ItemEvent.SELECTED){
          agenda = "Queue";
          statusbar.setText("use queue agenda");
        }
      }
    });

    group.add(astar);
    group.add(stack);
    group.add(queue);
    menubar.add(agendas);

    setJMenuBar(menubar);

    addKeyListener(new PauseKeyListener());
  }

  private void start(){
    new Thread(new Runnable() {
      public void run() {
        System.err.println("start solver");
        isStarted = true;
        loadMaze();
        solver = new MazeSolver(maze, getAgenda());
        boolean success = solver.solve();
        if(success == true){
          System.out.println("This maze is solved:");
          System.out.println(maze);
          statusbar.setText("solved");
        }else{
          System.out.println("This maze is not solvable:");
          System.out.println(maze);
          statusbar.setText("not solvable");
        }
        isStarted = false;
      }
    }).start();
  }

  private void pause()  {
    if(!isStarted)
      return;
    solver.pause();
    if(solver.isPaused()){
      statusbar.setText("animation is paused.");
    }else{
      statusbar.setText("");
    }
  }

  private Agenda<Square> getAgenda(){
    if(agenda.equals("A*")){
      return new AStar<Square>();
    }else if(agenda.equals("Stack")){
      return new MyStack<Square>();
    }else{
      return new MyQueue<Square>();
    }
  }

  private void loadMaze(){
    try {
      maze.loadFile(new File(mazeFile));
      maze.repaint();
    } catch (FileNotFoundException e1) {
      JOptionPane.showMessageDialog(maze, "Could not open file!",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private class OpenFileAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (isStarted){
        return;
      }

      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));
      int ret = chooser.showDialog(maze, "Load maze file");
      if (ret == JFileChooser.APPROVE_OPTION) {
        mazeFile = chooser.getSelectedFile().getName();
        System.err.println("file name: "+mazeFile);
        // replace old maze with new maze
        loadMaze();
      }
    }
  }

  private class SolveMazeAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (isStarted){
        return;
      }
      start();
    }
  }

  private class PauseKeyListener extends KeyAdapter{
    @Override
    public void keyPressed(KeyEvent e) {
      if (!isStarted){
       return;
      }

      int keycode = e.getKeyCode();

      if (keycode == 'p' || keycode == 'P') {
       pause();

       return;
      }
    }
  }

  public static void main(String[] args) {
    MazeApp app = new MazeApp();
    app.setVisible(true);
  }
}
