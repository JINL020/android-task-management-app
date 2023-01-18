package at.ac.univie.se2_team_0308.utils.typeConverters;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import at.ac.univie.se2_team_0308.utils.notifications.BasicNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.INotifier;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;
import at.ac.univie.se2_team_0308.utils.notifications.PopupNotifier;

public class INotifierTypeConverterTest {
    private INotifierTypeConverter converter;

    @Before
    public void setUp() {
        converter = new INotifierTypeConverter();
    }

    @Test
    public void fromINotifier_toINotifier(){
        INotifier notifier = new BasicNotifier(new PopupNotifier(new LoggerCore()));
        String json = converter.fromINotifier(notifier);
        INotifier fromJsonNotifier = converter.toINotifier(json);

        assertEquals(notifier.getNotifierType(), fromJsonNotifier.getNotifierType());
    }

}