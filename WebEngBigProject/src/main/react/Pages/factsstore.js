import create from 'zustand';

const useFactsStore = create((set, get) => ({
  facts: [],
  count: 5,
  setFacts: (facts) => set({ facts }),
  setCount: (count) => set({ count }),
  fetchFacts: async () => {
    const { count } = get(); 
    try {
      const response = await fetch(`/facts?count=${count}`);
      const data = await response.json();
      set({ facts: data });
    } catch (error) {
      console.error('Error fetching facts:', error);
    }
  },
}));

export default useFactsStore;
