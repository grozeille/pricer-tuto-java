package org.grozeille.pricer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.grozeille.pricer.utils.DateOffsetConverter;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.ParameterConverters.ParameterConverter;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith(JUnitReportingRunner.class)
public class AllStoriesTest extends JUnitStories {

    private final CrossReference xref = new CrossReference();

    public AllStoriesTest() {
        configuredEmbedder()
                .embedderControls()
                .doGenerateViewAfterStories(true)
                .doIgnoreFailureInStories(false)
                .doIgnoreFailureInView(true)
                .doVerboseFailures(true)
                .useThreads(2)
                .useStoryTimeoutInSecs(60);
    }

    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        URL codeLocation = CodeLocations.codeLocationFromClass(embeddableClass);
        StoryReporterBuilder storyReporter = 
        new StoryReporterBuilder() 
                .withCodeLocation(codeLocation) 
                .withDefaultFormats() 
                .withFormats(org.jbehave.core.reporters.Format.CONSOLE, 
                        org.jbehave.core.reporters.Format.HTML_TEMPLATE) 
                .withFailureTrace(true) 
                .withFailureTraceCompression(true) 
                .withCrossReference(xref);
        return new MostUsefulConfiguration() 
                .useStoryLoader(new LoadFromClasspath(embeddableClass)) 
                .useStoryReporterBuilder(storyReporter) 
                .useStepMonitor(xref.getStepMonitor())
                .useParameterConverters(new ParameterConverters().addConverters(getCustomConverters()));
    }

    private ParameterConverter[] getCustomConverters() {
        List<ParameterConverter> converters = new ArrayList<ParameterConverter>();
        
        converters.add(new DateOffsetConverter());
        
        return converters.toArray(new ParameterConverter[converters.size()]);
    }

    @Override
    protected List<String> storyPaths() {
        URL searchInURL = CodeLocations.codeLocationFromClass(this.getClass());
        return new StoryFinder().findPaths(searchInURL, "**/*.story", "");
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        
        applicationContext.scan(this.getClass().getPackage().getName()+".stories");
        applicationContext.refresh();
        
        return new SpringStepsFactory(configuration(), applicationContext);
    }
}