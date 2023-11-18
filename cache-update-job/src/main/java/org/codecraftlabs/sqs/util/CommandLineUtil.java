package org.codecraftlabs.sqs.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandLineUtil {
    public static final String CONFIGURATION_FILE = "c";

    private static final Logger logger = LogManager.getLogger(CommandLineUtil.class);

    private final CommandLineParser commandLineParser;

    final private static Options cmdLineOpts = new Options()
            .addRequiredOption(CONFIGURATION_FILE, AppArguments.CONFIGURATION_FILE, true, "Configuration file");

    public CommandLineUtil() {
        commandLineParser = new DefaultParser();
    }

    public AppArguments parse(String[] args) throws CommandLineException {
        logger.info("Parsing command line arguments");

        final Map<String, String> options = new HashMap<>();

        try {
            CommandLine cmdLine = commandLineParser.parse(cmdLineOpts, args);
            options.put(AppArguments.CONFIGURATION_FILE, cmdLine.getOptionValue(CONFIGURATION_FILE));
            return new AppArguments(options);
        } catch (ParseException exception) {
            logger.error("Command line parse error", exception);
            throw new CommandLineException("Error when parsing command line options", exception);
        }
    }

    public static void help() {
        var header = "\nAWS SQS producer sandbox app\n";
        var footer = "\nThank you for using\n";
        new HelpFormatter().printHelp("java -jar aws-sqs-producer-all.jar", header, cmdLineOpts, footer, true);
    }
}
