import * as actionType from "../actions/ActionType";
const initialState = {
  books: [],
  pagingConfig: {
    offset: 1,
    limit: 20,
    total: 0
  }
};

export default function bookReducer(state = initialState, action) {
  switch (action.type) {
    case actionType.GET_BOOK_LIST:
      return {
        ...state
      };
    case actionType.GET_BOOK_LIST_SUCCESS:
      return {
        ...state,
        books: [...state.books, ...action.payload.books],
        pagingConfig: action.payload.paging
      };
    case actionType.UPDATE_BOOKS_SUCCESS:
      const { listBooks } = action.payload;
      const newBooks = state.books.map(book => {
        const tempList = listBooks.filter(b => b.id === book.id);
        return tempList.length ? tempList[0] : book;
      });
      return {
        ...state,
        books: newBooks
      };
    case actionType.DELETE_BOOKS_SUCCESS:
        const {selectedIds} = action.payload;
        return {
            ...state,
            books: state.books.filter(b=> selectedIds.indexOf(b.id)!== -1)
        }

    default:
      return state;
  }
}
