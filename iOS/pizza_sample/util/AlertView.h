//
// AlertView.h
//

#import <UIKit/UIKit.h>

//
// AlertViewクラス
//
@interface AlertView : UIAlertView <UIAlertViewDelegate>

@property (strong) void (^completion)(NSInteger);

// AlertView初期化
- (id)initWithTitle:(NSString *)title message:(NSString *)message completion:(void (^)(NSInteger buttonIndex))completion cancelButtonTitle:(NSString *)cancelButtonTitle otherButtonTitles:(NSString *)otherButtonTitles, ... NS_REQUIRES_NIL_TERMINATION;

@end
