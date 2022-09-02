package com.atguigu.spring.service.impl;

import com.atguigu.spring.dao.BookDao;
import com.atguigu.spring.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * Date:2022/7/6
 * Author:ybc
 * Description:
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    /*@Transactional(
            //readOnly = true
            //timeout = 3
            //noRollbackFor = ArithmeticException.class
            //noRollbackForClassName = {"java.lang.ArithmeticException"}  //只有一個時，大括號可加可不加
            //isolation = Isolation.DEFAULT
            propagation = Propagation.REQUIRES_NEW
    )*/
    //mysql預設情況下查詢書價、庫存減一、更新餘額個獨佔一事務且自動提交，萬一用戶餘額不夠，只有第三事務會回滾
    public void buyBook(Integer userId, Integer bookId) {
        /*try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //查询图书的价格
        Integer price = bookDao.getPriceByBookId(bookId);
        //更新图书的库存
        bookDao.updateStock(bookId);
        //更新用户的余额
        bookDao.updateBalance(userId, price);
        //System.out.println(1/0);
    }
}
