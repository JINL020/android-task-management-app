package at.ac.univie.se2_team_0308.views;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mockito;

@RunWith(AndroidJUnit4.class)
public class AbstractFragmentTest {

    @Test
    public void AbstractClassCallsInitMethod_DependantMethodsAreCalled() {
        ATaskListFragment aTaskListFragment = Mockito.mock(ATaskListFragment.class, Answers.CALLS_REAL_METHODS);

        aTaskListFragment.initLayout();
        Mockito.verify(aTaskListFragment, Mockito.times(1)).initRecyclerViews();
        Mockito.verify(aTaskListFragment, Mockito.times(1)).initUtils();
        Mockito.verify(aTaskListFragment, Mockito.times(1)).initViewModel();
        Mockito.verify(aTaskListFragment, Mockito.times(1)).initViews();
    }
}
