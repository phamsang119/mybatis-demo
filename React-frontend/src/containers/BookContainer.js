import React from "react";
import { connect } from "react-redux";
import { Editors } from "react-data-grid-addons";
import { getBookList, updateBooks, deleteBooks } from "../actions/BookAction";
import BookComponent from "../components/BookComponent";

class BookContainer extends React.Component {
  state = {
    editedBooks: [],
    selectedIndexes: [],
    selectedIds: []
  };
  componentDidMount() {
    const { pagingConfig } = this.props.books;
    this.props.getBookList({
      api: "books",
      param: { page: pagingConfig.offset, limit: pagingConfig.limit }
    });
  }

  onRowsSelected = rows => {
    this.setState({
      selectedIndexes: this.state.selectedIndexes.concat(
        rows.map(r => r.rowIdx)
      ),
      selectedIds: this.state.selectedIds.concat(rows.map(r => r.row.id))
    });
  };

  onRowsDeselected = rows => {
    let rowIndexes = rows.map(r => r.rowIdx);
    this.setState({
      selectedIndexes: this.state.selectedIndexes.filter(
        i => rowIndexes.indexOf(i) === -1
      )
    });
  };

  onGridRowsUpdated = ({ fromRow, toRow, updated }) => {
    const rows = this.props.books.books.slice();
    for (let i = fromRow; i <= toRow; i++) {
      rows[i] = { ...rows[i], ...updated };
      this.updateEditedBook(rows[i]);
    }
  };

  updateEditedBook = book => {
    if (book.id !== "") {
      let books = this.state.editedBooks.filter(b => b.id !== book.id);
      books.push(book);
      this.setState({ editedBooks: books });
    }
  };

  saveBook = () => {
    const {editedBooks} = this.state;
    if (editedBooks.length > 0) {
      this.props.updateBooks({api: "books/updateList",data: {listBooks: editedBooks}});
    }

  };

  onRowDelete = () => {
    const {selectedIds} = this.state;
   if(selectedIds.length){
     this.props.onRowDelete(selectedIds);
   }
  };

  getMoreBooks = () => {
    const { pagingConfig } = this.props.books;
    this.props.getBookList({
      api: "books",
      param: { page: pagingConfig.offset + 1, limit: pagingConfig.limit }
    });
  };

  addBook = () => {
    const newBook = {
      id: "",
      bookName: "",
      author: "",
      description: "",
      price: 0,
      category: ""
    };
    this.setState({ books: [newBook, ...this.state.books] });
  };

  render() {
    const { books } = this.props.books;

    const { DropDownEditor } = Editors;

    const Categories = [
      { id: "it", value: "IT" },
      { id: "cooking", value: "Cooking" },
      { id: "teen", value: "Teen" }
    ];
    const categoryTypeEditor = <DropDownEditor options={Categories} />;

    const columns = [
      { key: "id", name: "ID", sortable: true },
      { key: "bookName", name: "Book Name", editable: true, sortable: true },
      { key: "author", name: "Author", editable: true, sortable: true },
      {
        key: "description",
        name: "Description",
        editable: true,
        sortable: true
      },
      { key: "price", name: "Price", editable: true, sortable: true },
      {
        key: "category",
        name: "Category",
        editor: categoryTypeEditor,
        sortable: true
      }
    ];

    return (
      <BookComponent
        columns={columns}
        books={books}
        pagingConfig={this.props.books.pagingConfig}
        getMoreBooks={this.getMoreBooks}
        saveBooks={this.saveBook}
        onRowDelete={this.onRowDelete}
        onRowsSelected= {this.onRowsSelected}
        onGridRowsUpdated = {this.onGridRowsUpdated}
        onRowsDeselected = {this.onRowsDeselected}
        editedBooks = {this.state.editedBooks}
        selectedIndexes = {this.state.selectedIndexes}
        selectedIds = {this.state.selectedIds}
      ></BookComponent>
    );
  }
}

const mapStateToProps = state => {
  return {
    books: state.books
  };
};

const mapDispatchToProps = {
  getBookList,
  updateBooks,
  deleteBooks
};

export default connect(mapStateToProps, mapDispatchToProps)(BookContainer);
