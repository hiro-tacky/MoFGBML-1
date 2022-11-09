package cilabo.gbml.component.replacement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.uma.jmetal.component.replacement.Replacement;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.comparator.ObjectiveComparator;

import cilabo.main.Consts;

/**
 * 通常のルール置換型ミシガン操作.
 *
 */
public class RuleReplacementStyleReplacement
	implements Replacement<IntegerSolution> {
	public List<IntegerSolution> replace(List<IntegerSolution> currentList, List<IntegerSolution> offspringList) {

		// 親個体をfitness順にソートする
		Collections.sort(offspringList,
						 new ObjectiveComparator<IntegerSolution>(0, ObjectiveComparator.Ordering.DESCENDING));

		List<IntegerSolution> buf = new ArrayList<>();

		// Replace rules from bottom of list.
		for(int i = 0; i < Math.min(offspringList.size(), Consts.MAX_RULE_NUM); i++) {
			buf.add(offspringList.get(i));
		}

		return buf;
	}

}
