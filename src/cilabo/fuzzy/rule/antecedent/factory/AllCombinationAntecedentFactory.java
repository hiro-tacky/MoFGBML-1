package cilabo.fuzzy.rule.antecedent.factory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import cilabo.fuzzy.knowledge.Knowledge;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.antecedent.AntecedentFactory;

/** KB上の全てのファジィ集合の組み合わせを持つ前件部を生成
 * @author hirot
 *
 */
public class AllCombinationAntecedentFactory implements AntecedentFactory {
	/** Internal parameter */
	int[][] antecedents;
	private int head = 0;

	/**
	 * コンストラクタ
	 */
	public AllCombinationAntecedentFactory() {
		init();
	}

	/**
	 * 全ての組わせを生成する．
	 */
	private void init() {
		int dimension = Knowledge.getInstace().getDimension();

		Queue<ArrayList<Integer>> que = new ArrayDeque<>();
		ArrayList<ArrayList<Integer>> ids = new ArrayList<>();

		que.add(new ArrayList<Integer>());

		while(!que.isEmpty()){
			ArrayList<Integer> buf = que.poll();
			int dim_i = buf.size();
			if(dim_i < dimension) {
				for(int i=0; i < Knowledge.getInstace().getFuzzySetNum(dim_i); i++) {
					ArrayList<Integer> tmp = (ArrayList<Integer>) buf.clone();
					tmp.add(i);
					que.add(tmp);
				}
			}else {
				ids.add(buf);
			}
		}

		// Antecedent Part
		antecedents = new int[ids.size()][dimension];
		for(int i=0; i<ids.size(); i++) {
			for(int dim_i=0; dim_i<dimension; dim_i++) {
				antecedents[i][dim_i] = ids.get(i).get(dim_i);
			}
		}

	}

	/**
	 *
	 */
	@Override
	public Antecedent create() {
		if(head >= antecedents.length) return null;

		int[] antecedentIndex = this.antecedents[head];
		head++;

		return Antecedent.builder()
						.antecedentIndex(antecedentIndex)
						.build();
	}

	public int getRuleNum() {
		return this.antecedents.length;
	}

	public static AllCombinationAntecedentFactory.AllCombinationAntecedentFactoryBuilder builder() {
		return new AllCombinationAntecedentFactoryBuilder();
	}

	public static class AllCombinationAntecedentFactoryBuilder {

		AllCombinationAntecedentFactoryBuilder() {}

		public AllCombinationAntecedentFactory build() {
			return new AllCombinationAntecedentFactory();
		}
	}

}
