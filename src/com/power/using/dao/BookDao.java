package com.power.using.dao;

import java.util.List;

import com.power.using.domain.Book;

public interface BookDao {

	void save(Book book);

	/**
	 * 把书籍对应的字段查询出来
	 * @param bookId
	 * @return
	 */
	Book findBookById(String bookId);

	/**
	 * 获取书籍的总记录数
	 * @return
	 */
	int getToatalRecordsNum();

	/**
	 * 查出书籍对应的分类
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List findPageRecoeds(int startIndex, int pageSize);

	/**
	 * 根据分类的id获取总记录的条数
	 * @param categoryId
	 * @return
	 */
	int getToatalRecordsNum(String categoryId);

	/**
	 * 查出书籍对应的分类
	 * @param startIndex
	 * @param pageSize
	 * @param categoryId
	 * @return
	 */
	List findPageRecoeds(int startIndex, int pageSize, String categoryId);

}
