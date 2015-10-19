//
// ActionSheet.h
//

#import <UIKit/UIKit.h>

//
// ActionSheetクラス
//
@interface ActionSheet : UIActionSheet <UIActionSheetDelegate>

// AlertView初期化
- (id)initWithTitle:(NSString *)title completion:(void (^)(NSInteger buttonIndex))completion cancelButtonTitle:(NSString *)cancelButtonTitle destructiveButtonTitle:(NSString *)destructiveButtonTitle otherButtonTitles:(NSString *)otherButtonTitles, ... NS_REQUIRES_NIL_TERMINATION;

@end
