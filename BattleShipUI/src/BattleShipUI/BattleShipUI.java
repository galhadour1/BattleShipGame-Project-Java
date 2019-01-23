package BattleShipUI;

import BattleShipLogic.*;

import java.awt.*;
import java.util.Scanner;


public class BattleShipUI {
    private Menu m_Menu =new Menu();
    private BattleShipLogic m_BattleShipGame =new BattleShipLogic();
    private eMenuType m_UserChoice =eMenuType.WaitingForAction;
    private String m_PathXmlFile;
    private boolean m_IsXmlFileLoaded = false;

    public void RunBattleShipGame(){
        eInputProblems inputProblems=eInputProblems.IsChoiceValid;
        while (m_UserChoice !=eMenuType.ExitGame){
            GetUserChoice();
            OperateChoice(m_UserChoice);
            if (m_UserChoice ==eMenuType.StartTheGame)
            {
                while (m_UserChoice !=eMenuType.ExitGame && m_UserChoice !=eMenuType.EndCurrentGame){
                    GetUserChoice();
                    OperateChoice(m_UserChoice);
                }
            }
            if(m_UserChoice ==eMenuType.ExitGame)
                printByeBye();
        }
    }

    public void GetUserChoice(){
        eInputProblems inputProblems=eInputProblems.IsChoiceValid;
        m_Menu.PrintMenu();
        do {
            m_UserChoice = eMenuType.getMenuType(ReadUserChoice());
            inputProblems = m_BattleShipGame.CheckUserChoice(m_UserChoice);
            if (inputProblems!=eInputProblems.IsChoiceValid)
                PrintInputProblem(inputProblems);
        }while (!inputProblems.equals(eInputProblems.IsChoiceValid));
    }

    public int ReadUserChoice(){
        int userChoice=0;
        Scanner s;
        int maxNumUserChoice= m_Menu.m_ArrMenuTypes.size();
        boolean isInputValid=false;

        System.out.println(String.format("Please type number of command from this menu list, 1-%d",maxNumUserChoice));
        while (!isInputValid) {
            s = new Scanner(System.in);
            if (s.hasNextInt())
            {
                userChoice=s.nextInt();
                if (userChoice<1 || userChoice>maxNumUserChoice)
                    System.out.println(String.format("User choice is out of range! Please enter a number BETWEEN 1 to %d!",maxNumUserChoice));
                else {
                    isInputValid=true;
                }
            }
            else
            {
                System.out.println("You have to type a command's NUMBER!");
            }
        }

        return userChoice;
    }

    private void SetIsXmlFileLoaded()
    {
        m_IsXmlFileLoaded =true;
    }

    private void DisplayGameStatistics(){
        Object[] statusInfo = m_BattleShipGame.GetStatisticsInfo();

        System.out.println("Statistics Of The Game:");
        System.out.println(String.format("Attacks counter: %d", (int)statusInfo[0]));
        System.out.println(String.format("Time of game that has passed: %s", (GameTime)statusInfo[1]));
        System.out.println("Statistics of Player A:");
        System.out.println(String.format("  Player score: %d", (int)statusInfo[2]));
        System.out.println(String.format("  Number of miss: %d", (int)statusInfo[3]));
        System.out.println(String.format("  Average time of attacks in the game: %s", (GameTime)statusInfo[4]));
        System.out.println("Statistics of Player B:");
        System.out.println(String.format("  Player Score: %d", (int)statusInfo[5]));
        System.out.println(String.format("  Number of miss: %d", (int)statusInfo[6]));
        System.out.println(String.format("  Average time of attacks in the game: %s", (GameTime)statusInfo[7]));
    }

    private void OperateChoice(eMenuType menuType)
    {
        boolean isWin=false;

        if (m_BattleShipGame.getStatusGame().equals(eStatusGame.GameStarted) && menuType!=eMenuType.ExitGame && menuType!=eMenuType.EndCurrentGame){
            PrintBoards();
        }
        
        switch (menuType)
        {
            case LoadXmlFile:
                OperateLoadXmlFile();
                break;
            case StartTheGame:
                CheckRestart();
                m_BattleShipGame.StartTheGame();
                SetIsXmlFileLoaded();
                PrintBoards();
                break;
            case GameStatus:
                PrintScore();
                break;
            case AttackOpponent:
                m_BattleShipGame.StartMoveTime();
                isWin= OperateAttackOpponent();
                break;
            case GameStatistics:
                DisplayGameStatistics();
                break;
            case Trap:
                SetTrapOnBoard();
                break;
            case EndCurrentGame:
                PrintEndGame();
                break;
        }

       if(isWin)
       {
           eMenuType.StartTheGame.setIsMenuTypeActive(true);
       }
    }


    private boolean OperateAttackOpponent(){
        Scanner s;
        boolean isAttackValid=false;
        eAttackResults attackResults;
        int x=0;
        int y=0;
        Point point;
        boolean isWin=false;

        System.out.println(String.format("Please choose a square (two numbers between 1 to %d) for attacking the opponent ", m_BattleShipGame.getBoardSize()));
        while (!isAttackValid){
            s=new Scanner(System.in);
            System.out.print("Row: ");
            if (s.hasNextInt() && x==0 )
                x=s.nextInt();
            System.out.print("Column: ");
            if (s.hasNextInt()&& y==0)
                y=s.nextInt();
            if (y==0 || x==0)
                System.out.println(String.format("You have to enter a NUMBER between 1 to %d", m_BattleShipGame.getBoardSize()));
            point=new Point(y-1,x-1);
            attackResults= m_BattleShipGame.AttackOpponent(point);
            switch (attackResults){
                case AttackEndedWithHit:
                    System.out.println("\nWell Done! You hit a ship! ^_^\n");
                    isAttackValid=true;
                    break;
                case AttackEndedWithMiss:
                    System.out.println("\nYou miss the ships :( ...Maybe next time\n");
                    isAttackValid=true;
                    break;
                case AttackTrapWithObject:
                    System.out.println("\nYou attack the trap and there is an object\n");//change TODO
                    isAttackValid=true;
                    break;
                case AttackTrapWithOutObject:
                    System.out.println("\nYou attack the trap and there is not an object\n");//chang TODO
                    isAttackValid=true;
                    break;
                case HitBefore:
                    System.out.println("You hit this square before! Please choose a new square");
                    x=0;y=0;
                    break;
                case OutOfRange:
                    System.out.println(String.format("The square you chose is out of range!Please choose a new square(two numbers between 1 to %d) to attack", m_BattleShipGame.getBoardSize()));
                    x=0;y=0;
                    break;
                case Win:
                    System.out.println(String.format("Player %c Win!!!", m_BattleShipGame.FindWinners()+'A'));
                    isAttackValid=true;
                    isWin=true;
                    m_UserChoice =eMenuType.EndCurrentGame;
                    break;
            }
        }

        return isWin;
    }

    private void SetTrapOnBoard() {
        Scanner s;
        boolean isSetValid = false;
        eAttackResults trapResults;
        int x = 0;
        int y = 0;
        Point point;
        boolean isUserChoicePointValid=true;

        System.out.println(String.format("Please choose a square (two numbers between 1 to %d) for setting the trap ", m_BattleShipGame.getBoardSize()));
        while (!isSetValid) {
            s = new Scanner(System.in);
            System.out.print("Row: ");
            if (s.hasNextInt() && x == 0)
                x = s.nextInt();
            System.out.print("Column: ");
            if (s.hasNextInt() && y == 0)
                y = s.nextInt();
            if (y == 0 || x == 0)
                System.out.println(String.format("You have to enter a NUMBER between 1 to %d", m_BattleShipGame.getBoardSize()));
            point = new Point(y - 1, x - 1);
            isUserChoicePointValid = m_BattleShipGame.SetTrapOnBoard(point);
            if(!isUserChoicePointValid)
            {
                System.out.println(String.format("Your point is not valid , you have to choose a point ,You must select a point with a distance of one square  from the object "));
                x=0;
                y=0;
            }
            else
                isSetValid=true;
        }
    }


    private void OperateLoadXmlFile(){
        Scanner s;
        boolean isPathForXmlValid=false;
        String xmlSuffix=".xml";
        
        System.out.println("Please enter the full path for the xml with the xml suffix:");
        while (!isPathForXmlValid) {
            s = new Scanner(System.in);
           m_PathXmlFile = s.nextLine();
            if (!m_PathXmlFile.endsWith(xmlSuffix))
                System.out.println("You have to enter xml path with xml suffix! This is not a Xml file!! ");
            else{
                isPathForXmlValid= LoadXmlFile(isPathForXmlValid);
            }
        }
        System.out.println("Xml file Loaded successfully!!\n");
    }

    private boolean LoadXmlFile(boolean isPathValid)
    {
        eProblemsXML problemsXml;

        problemsXml= m_BattleShipGame.LoadItemsFromXML(m_PathXmlFile);
        if (problemsXml== eProblemsXML.XmlIsValid)
            isPathValid=true;
        else PrintXmlProblems(problemsXml);

        return  isPathValid;
    }

    private void CheckRestart()
    {
        boolean isValid=false;

        if(m_IsXmlFileLoaded)
        {
            m_BattleShipGame.ClearAllBoards();
            isValid= LoadXmlFile(isValid);
        }
    }

    public void PrintScore()
    {
        System.out.println(String.format("Player %c score: %d\n", m_BattleShipGame.getAttackerPlayer()+'A', m_BattleShipGame.getAttackerPlayerScore()));
    }

    public void PrintInputProblem(eInputProblems inputProblems){
        switch (inputProblems){
            case OperateOtherCommandBeforeLoading:
                System.out.println("You have to choose load from xml or load from file FIRST!");
                break;
            case OperateGameActionBeforeStarted:
                System.out.println("You have to choose to play against computer or one of the load options!");
                break;
            case OperateSettingsCommandWhenGameIsOn:
                System.out.println("You have to choose another command! This is a pregame command!!");
                break;
        }
    }

    private void PrintXmlProblems(eProblemsXML xmlProblems){
        switch (xmlProblems){
            case XmlDoesNotExist:
                System.out.println("Xml file does not exist in this PATH!");
                break;
            case XmlBoardSizeInvalid:
                System.out.println("The size of the board in xml file file is illegal! It has to be BETWEEN 5-20!");
                break;
            case XmlShipItemsNumberInvalid:
                System.out.println("The number of ship items in xml file is not the same as the amount in the specific xml file!");
                break;
            case XmlShipPositionInvalid:
                System.out.println("The ship positions in the xml file is illegal!");
                break;
        }
    }

    public void PrintEndGame()
    {
        printTechnicalVictory();
        PrintShipBoardAttackerPlayer();
        PrintShipBoardPlayerOnBreak();
        DisplayGameStatistics();
        printByeBye();
        eMenuType.StartTheGame.setIsMenuTypeActive(true);
    }

    private void PrintGameBoard(char[][] board)
    {
        System.out.print("\\ ");
        for (int i = 0; i< m_BattleShipGame.getBoardSize(); i++) {
            System.out.print(' ');
            System.out.print((char)('A'+i));
            System.out.print(' ');
            System.out.print(' ');
        }
        System.out.println();
        for (int i = 0; i< m_BattleShipGame.getBoardSize(); i++)
        {
            System.out.print(String.format("%d ",i+1));
            for (int j = 0; j< m_BattleShipGame.getBoardSize(); j++){
                System.out.print(String.format("|%c| ",board[i][j]));
            }
            System.out.println();
        }
        System.out.println();
    }

    private void PrintShipBoardAttackerPlayer(){
        System.out.println(String.format("Player %c:", m_BattleShipGame.getAttackerPlayer()+'A'));
        System.out.println("ship board:");
        PrintGameBoard(m_BattleShipGame.getAttackerPlayerShipBoard());
    }

    private void PrintShipBoardPlayerOnBreak(){
        System.out.println(String.format("Player %c:", m_BattleShipGame.getPlayerOnBreak()+'A'));
        System.out.println("ship board:");
        PrintGameBoard(m_BattleShipGame.getPlayerOnBreakShipBoard());
    }

    private void PrintTrackBoard(){
        System.out.println("Track board:");
        PrintGameBoard(m_BattleShipGame.getAttackerPlayerTrackBoard());
    }

    private void printTechnicalVictory()
    {
        System.out.println(String.format("Player %c win because player %c left the game", m_BattleShipGame.getPlayerOnBreak()+'A', m_BattleShipGame.getAttackerPlayer()+'A'));
    }

    private void printByeBye()
    {
        System.out.println("Thanks for playing!\n Bye! Bye! ;)");
    }

    private void PrintBoards(){
        System.out.println(String.format("Player %c", m_BattleShipGame.getAttackerPlayer()+'A'));
        PrintShipBoardAttackerPlayer();
        PrintTrackBoard();
    }
}
