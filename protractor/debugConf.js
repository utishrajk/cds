
exports.config = {
    // A base URL for your application under test. Calls to protractor.get()
    // with relative paths will be prepended with this.
//    baseUrl: 'http://bhamdevtest001:8080/bham/#/',
//    baseUrl: 'http://localhost:9001/bham/#/',
    baseUrl: 'http://qa-cds.cfapps.io/#/',
//    baseUrl: 'http://prod-cds.cfapps.io/#/',

    // The address of a running selenium server. If specified, Protractor will
    // connect to an already running instance of selenium. This usually looks like
    // seleniumAddress: 'http://localhost:4444/wd/hub',

    // Spec patterns are relative to the location of this config.
    specs: ['../src/main/webapp/source/**/*.e2e.js'],

    // Do not start a Selenium Standalone sever - only run this using chrome.
    chromeOnly: true,
    chromeDriver: null,

    // Capabilities to be passed to the webdriver instance.
    capabilities: {
        'browserName': 'chrome'
//        'chromeOptions': {
//            'args': ['show-fps-counter=true']
//        }
    },
    
    // Options to be passed to Jasmine-node.
    jasmineNodeOpts: {
        showColors: true,
        includeStackTrace: true,
        defaultTimeoutInterval: 30000
    },

    // The params object will be passed directly to the protractor instance,
    // and can be accessed from your test. It is an arbitrary object and can
    // contain anything you may need in your test.
    // This can be changed via the command line as:
    //   --params.login.user 'Joe'
    onPrepare: function() {
        browser.driver.manage().window().maximize();
        browser.get('#/login');
        element(by.model('username')).sendKeys('tmngassa');
        element(by.model('password')).sendKeys('pass');
        element(by.id('loginSubmitBtn')).click();

    },

    // ----- The cleanup step -----
    //
    // A callback function called once the tests have finished running and
    // the webdriver instance has been shut down. It is passed the exit code
    // (0 if the tests passed or 1 if not).
    onCleanUp: function() {
        browser.get('#/logout');
    },

    // The params object will be passed directly to the protractor instance,
    // and can be accessed from your test. It is an arbitrary object and can
    // contain anything you may need in your test.
    // This can be changed via the command line as:
    //   --params.login.user 'Joe'
    // Params can be access in the test through the browser object like browser.params
    params: {
        user: {
            username: 'tmngassa',
            password: 'pass'
        }
    }
};
