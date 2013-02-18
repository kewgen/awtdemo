package com.geargames.awtdemo.app;

/**
 * User: abarakov
 * Date: 08.02.13 12:37
 */
public class Engine {

    /*
    com.geargames.app
    com.geargames.env
    com.geargames.engine
    */

    /*
    private static Engine instance = new Engine();

    public static Engine getInstance() {
        return instance;
    }

    private Engine() {
    }

    private static Application application = Application.getInstance();

    private static Application getApplication()
    {
         return application;
    }

    private static PPanelManager panelManager = PPanelManager.getInstance();

    private static PPanelManager getPPanelManager()
    {
        return panelManager;
    }
    */



    /*
    // Engine
    public abstract class Main extends Logger { // com.geargames.Main

        public Application createApplication()
        {
            return new Application();
        }

        public void commonMain(String[] args) throws IOException {
            // ...

            createApplication();
//            Application.createInstance();
            Application.run();
        }


    }

    public final class awtdemo extends Main {

        @Override
        public Application createApplication()
        {
            return new AWTDemoApplication();
        }

        public static void main(String[] args) throws IOException {
            LOG_FILE_NAME = "ggproject";
            awtdemo main = new awtdemo();
            main.commonMain(args);
        }


    }

    public abstract class Application
    {
        private static Application instance;

        private Port port;
        private MIDlet midlet;
        private Render render;
        private Manager manager;
        private PPanelManager panelManager;

        private Log log;

        public Application()
        {
            instance = this;
            port = createPort();
            midlet  = createMIDlet();
            render  = createRender();
            manager = createManager();
            panelManager = createPanelManager();
        }


        // Эти методы можно перенести в класс Main/Engine
        // Этот метод можно перекрыть, чтобы создать главное окно (форму) другого класса
        protected Port createPort()
        {
            return new Port();
        }

        protected MIDlet createMIDlet()
        {
            return new MIDlet();
        }

        protected Render createRender()
        {
            return new Render();
        }

        protected Manager createManager()
        {
            return new Manager(render);
        }

        protected PPanelManager createPanelManager()
        {
            return new PPanelManager();
        }

        public static MIDlet getMIDlet()
        {
            return instance.midlet;
        }

        public static Render getRender()
        {
            return instance.render;
        }

        public static Manager getManager()
        {
            return instance.manager;
        }

    }


// Engine.application().showHint();


*/
}


