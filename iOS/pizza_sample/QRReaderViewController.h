//
//  QRReaderViewController.h
//  pizza_sample
//
//  Created by YokoyamaDaisuke on 2015/10/21.
//  Copyright © 2015年 YokoyamaDaisuke. All rights reserved.
//

#import <UIKit/UIKit.h>

@class QRReaderViewController;

@protocol QRReaderDelegate <NSObject>

@optional

- (void)reader:(QRReaderViewController *)reader didScanResult:(NSString *)result;

@end

@interface QRReaderViewController : UIViewController

@property (nonatomic, weak) id<QRReaderDelegate> delegate;

@end
